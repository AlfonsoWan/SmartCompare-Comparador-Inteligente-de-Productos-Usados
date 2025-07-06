package com.smartcompare.product.application;

import com.smartcompare.product.domain.Product;
import com.smartcompare.product.domain.dto.ProductDTO;
import com.smartcompare.product.domain.dto.EbaySearchResponse;
import com.smartcompare.product.domain.dto.EbaySearchAndRankingResponse;
import com.smartcompare.product.domain.exception.ProductNotFoundException;
import com.smartcompare.product.infrastructure.ProductRepository;
import com.smartcompare.product.infrastructure.EbayApiClient;
import com.smartcompare.product.infrastructure.EbayOAuthService;
import com.smartcompare.smartranking.application.SmartRankingService;
import com.smartcompare.smartranking.domain.dto.SmartRankingResultDTO;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final EbayApiClient ebayApiClient;
    private final EbayOAuthService ebayOAuthService;
    private final SmartRankingService smartRankingService;
    private final SearchHistoryService searchHistoryService;
    private final RecommendationService recommendationService;

    @Transactional(readOnly = true)
    public ProductDTO findById(String id) {
        // Primero intenta buscar en la base de datos local
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            return toDTO(productOpt.get());
        }

        // Si no está, intenta buscar en eBay (o fuente externa)
        String token = ebayOAuthService.getAppAccessToken();
        if (token == null) {
            throw new ProductNotFoundException("No se pudo obtener el token de eBay");
        }
        // Aquí deberías tener un método para buscar un producto individual en eBay por su id
        ProductDTO ebayProduct = ebayApiClient.getProductById(id, token);
        if (ebayProduct != null) {
            return ebayProduct;
        }

        throw new ProductNotFoundException("Producto no encontrado: " + id);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAllPaged(int page, int size, String sortBy) {
        return productRepository.findAll().stream()
                .skip((long) page * size)
                .limit(size)
                .sorted((a, b) -> {
                    if (sortBy.equalsIgnoreCase("name")) {
                        return a.getName().compareToIgnoreCase(b.getName());
                    } else if (sortBy.equalsIgnoreCase("price")) {
                        return Double.compare(a.getPrice(), b.getPrice());
                    } else {
                        return a.getId().compareTo(b.getId());
                    }
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO save(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .image(dto.getImage())
                .source(dto.getSource())
                .url(dto.getUrl())
                .build();
        product = productRepository.save(product);
        return toDTO(product);
    }

    public EbaySearchAndRankingResponse searchInEbay(String query, Integer limit) {
        String token = ebayOAuthService.getAppAccessToken();
        if (token == null) {
            throw new RuntimeException("No se pudo obtener el token de eBay");
        }
        EbaySearchResponse ebayResponse = ebayApiClient.searchProducts(query, limit != null ? limit : 10, token);
        Long userId = getCurrentUserId();
        // Guardar historial de búsqueda automáticamente
        if (userId != null && query != null && !query.isBlank()) {
            searchHistoryService.save(query, userId);
        }
        SmartRankingResultDTO ranking = smartRankingService.analyzeAndSave(query, userId, ebayResponse.getItems());
        // Crear recomendaciones automáticas para los top 3 productos del ranking
        if (userId != null && ranking != null && ranking.getTopProductItemIds() != null) {
            for (String itemId : ranking.getTopProductItemIds()) {
                // Buscar el producto en la base de datos por URL
                Product product = productRepository.findAll().stream()
                        .filter(p -> p.getUrl() != null && p.getUrl().contains(itemId))
                        .findFirst().orElse(null);
                if (product != null) {
                    RecommendationDTO rec = RecommendationDTO.builder()
                            .suggestedProductId(product.getId()) // Ahora esto es compatible porque ambos son String
                            .reason(ranking.getJustification())
                            .userId(userId)
                            .build();
                    recommendationService.create(rec);
                }
            }
        }
        return new EbaySearchAndRankingResponse(ebayResponse, ranking);
    }

    private Long getCurrentUserId() {
        try {
            String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.parseLong(userIdStr);
        } catch (Exception e) {
            return null;
        }
    }

    private ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .source(product.getSource())
                .url(product.getUrl())
                .build();
    }
}
