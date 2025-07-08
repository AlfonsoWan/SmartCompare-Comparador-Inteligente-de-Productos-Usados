package com.smartcompare.recommendation.application;

import com.smartcompare.favorite.application.FavoriteService;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.favorite.infrastructure.FavoriteRepository;
import com.smartcompare.product.domain.Product;
import com.smartcompare.product.infrastructure.ProductRepository;
import com.smartcompare.recommendation.domain.Recommendation;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import com.smartcompare.recommendation.domain.exception.RecommendationNotFoundException;
import com.smartcompare.recommendation.infrastructure.RecommendationRepository;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final FavoriteService favoriteService;
    private final SearchHistoryService searchHistoryService;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public RecommendationDTO findById(Long id) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new RecommendationNotFoundException("Recomendación no encontrada: " + id));
        return toDTO(recommendation);
    }

    @Transactional(readOnly = true)
    public List<RecommendationDTO> findAll() {
        return recommendationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecommendationDTO> findByUserId(Long userId) {
        return recommendationRepository.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<RecommendationDTO> findByUserIdPaged(Long userId, Pageable pageable) {
        return recommendationRepository.findByUserId(userId, pageable).map(this::toDTO);
    }

    @Transactional
    public RecommendationDTO create(RecommendationDTO dto) {
        Recommendation recommendation = Recommendation.builder()
                .suggestedProductId(dto.getSuggestedProductId())
                .reason(dto.getReason())
                .userId(dto.getUserId())
                .build();
        Recommendation saved = recommendationRepository.save(recommendation);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!recommendationRepository.existsById(id)) {
            throw new RecommendationNotFoundException("Recomendación no encontrada: " + id);
        }
        recommendationRepository.deleteById(id);
    }

    private RecommendationDTO toDTO(Recommendation recommendation) {
        return RecommendationDTO.builder()
                .id(recommendation.getId())
                .suggestedProductId(recommendation.getSuggestedProductId())
                .reason(recommendation.getReason())
                .userId(recommendation.getUserId())
                .build();
    }

    public Long getUserIdFromRecommendation(Long recommendationId) {
        return findById(recommendationId).getUserId();
    }

    @Transactional(readOnly = true)
    public List<RecommendationDTO> getRecommendations(Long userId) {
        // 1. Obtener favoritos del usuario
        List<FavoriteDTO> favorites = favoriteService.findByUserId(userId);
        List<String> favoriteProductIds = favorites.stream()
                .map(FavoriteDTO::getProductId)
                .collect(Collectors.toList());

        // 2. Determinar semillas: categorías de favoritos o términos de búsqueda
        List<String> seedCategories;
        if (!favorites.isEmpty()) {
            seedCategories = favorites.stream()
                    .map(fav -> productRepository.findById(fav.getProductId())
                            .map(Product::getPrimaryCategoryId)
                            .orElse(null))
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
        } else {
            seedCategories = searchHistoryService.findByUserId(userId).stream()
                    .map(sh -> sh.getTerms())
                    .distinct()
                    .collect(Collectors.toList());
        }

        // 3. Si no hay semillas, no hay recomendaciones
        if (seedCategories.isEmpty()) {
            return Collections.emptyList();
        }

        // 4. Preparar exclusión de productos ya favoritos
        Set<String> excludeIds = new HashSet<>(favoriteProductIds);
        List<RecommendationDTO> recommendations = new ArrayList<>();

        // 5. Por cada categoría semilla, buscar hasta 5 ítems nuevos
        for (String categoryId : seedCategories) {
            if (recommendations.size() >= 5) break;
            Pageable page = PageRequest.of(0, 5);
            List<Product> similarProducts = productRepository
                    .findByPrimaryCategoryIdAndIdNotIn(categoryId, new ArrayList<>(excludeIds), page);

            for (Product p : similarProducts) {
                if (recommendations.size() >= 5) break;
                if (!excludeIds.contains(p.getId())) {
                    recommendations.add(RecommendationDTO.builder()
                            .userId(userId)
                            .suggestedProductId(p.getId())
                            .reason(!favorites.isEmpty()
                                    ? "Basado en tus favoritos"
                                    : "Basado en tu historial de búsqueda")
                            .build());
                    excludeIds.add(p.getId());
                }
            }
        }

        return recommendations;
    }
}

