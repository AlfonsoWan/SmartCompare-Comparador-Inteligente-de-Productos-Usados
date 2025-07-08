package com.smartcompare.recommendation.application;

import com.smartcompare.favorite.application.FavoriteService;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.product.domain.dto.EbayProductDTO;
import com.smartcompare.product.domain.dto.EbaySearchResponse;
import com.smartcompare.product.infrastructure.EbayApiClient;
import com.smartcompare.product.infrastructure.EbayOAuthService;
import com.smartcompare.recommendation.domain.Recommendation;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import com.smartcompare.recommendation.domain.exception.RecommendationNotFoundException;
import com.smartcompare.recommendation.infrastructure.RecommendationRepository;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;
    private final FavoriteService favoriteService;
    private final SearchHistoryService searchHistoryService;
    private final EbayOAuthService ebayOAuthService;
    private final EbayApiClient ebayApiClient;

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
        // 1) obtener token de eBay
        String token = ebayOAuthService.getAppAccessToken();

        // 2) cargar favoritos y preparar exclusión
        List<String> favIds = favoriteService.getFavoriteProductIds(userId);
        Set<String> exclude = new HashSet<>(favIds);

        // 3) resultados
        List<RecommendationDTO> recs = new ArrayList<>();

        // 4) si hay favoritos, intentar por categoría
        if (!favIds.isEmpty()) {
            // 4.1 extraer categorías no-nulas
            List<FavoriteDTO> favDtos = favoriteService.findByUserId(userId);
            List<String> categories = favDtos.stream()
                    .map(FavoriteDTO::getPrimaryCategoryId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .toList();

            // 4.2 si tenemos categorías, buscamos por categoría
            if (!categories.isEmpty()) {
                for (String catId : categories) {
                    if (recs.size() >= 5) break;
                    EbaySearchResponse resp = ebayApiClient.searchProductsByCategory(catId, 10, token);
                    if (resp != null && resp.getItems() != null) {
                        for (var item : resp.getItems()) {
                            if (recs.size() >= 5) break;
                            String id = item.getItemId();
                            if (exclude.add(id)) {
                                recs.add(buildDTO(userId, id, "Basado en tus favoritos"));
                            }
                        }
                    }
                }
                return recs;  // devolvemos ya si vinieron por categoría
            }

            // 4.3 fallback: si NO hay categorías en tus favoritos,
            //      vamos a tokenizar sus títulos y buscar por texto
            for (FavoriteDTO fav : favDtos) {
                if (recs.size() >= 5) break;
                String title = fav.getTitle();
                if (title == null) continue;
                for (String tok : title.toLowerCase().split("\\s+")) {
                    if (tok.length() < 4 || recs.size() >= 5) continue;
                    EbaySearchResponse resp = ebayApiClient.searchProducts(tok, 10, token);
                    if (resp != null && resp.getItems() != null) {
                        for (var item : resp.getItems()) {
                            if (recs.size() >= 5) break;
                            String id = item.getItemId();
                            if (exclude.add(id)) {
                                recs.add(buildDTO(userId, id, "Basado en tus favoritos"));
                            }
                        }
                    }
                }
            }
            if (!recs.isEmpty()) {
                return recs;
            }
        }

        // 5) si no hay favoritos o no se obtuvo nada, usamos el historial
        List<String> terms = searchHistoryService.getSearchHistoryTerms(userId);
        for (String term : terms) {
            if (recs.size() >= 5) break;
            for (String tok : term.toLowerCase().split("\\s+")) {
                if (tok.length() < 4 || recs.size() >= 5) continue;
                EbaySearchResponse resp = ebayApiClient.searchProducts(tok, 10, token);
                if (resp != null && resp.getItems() != null) {
                    for (var item : resp.getItems()) {
                        if (recs.size() >= 5) break;
                        String id = item.getItemId();
                        if (exclude.add(id)) {
                            recs.add(buildDTO(userId, id, "Basado en tu historial de búsqueda"));
                        }
                    }
                }
            }
        }

        // 6) si aún está vacío, devolvemos la lista vacía
        return recs;
    }

    private RecommendationDTO buildDTO(Long userId, String prodId, String reason) {
        return RecommendationDTO.builder()
                .userId(userId)
                .suggestedProductId(prodId)
                .reason(reason)
                .build();
    }
}

