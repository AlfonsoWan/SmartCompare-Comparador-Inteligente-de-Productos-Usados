package com.smartcompare.product.application;

import com.smartcompare.product.domain.Product;
import com.smartcompare.product.domain.dto.ProductDTO;
import com.smartcompare.product.domain.dto.EbaySearchResponse;
import com.smartcompare.product.domain.exception.ProductNotFoundException;
import com.smartcompare.product.infrastructure.ProductRepository;
import com.smartcompare.product.infrastructure.EbayApiClient;
import com.smartcompare.product.infrastructure.EbayOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final EbayApiClient ebayApiClient;
    private final EbayOAuthService ebayOAuthService;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado: " + id));
        return toDTO(product);
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
                        return Long.compare(a.getId(), b.getId());
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

    public EbaySearchResponse searchInEbay(String query, Integer limit) {
        String token = ebayOAuthService.getAppAccessToken();
        if (token == null) {
            throw new RuntimeException("No se pudo obtener el token de eBay");
        }
        return ebayApiClient.searchProducts(query, limit != null ? limit : 10, token);
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
