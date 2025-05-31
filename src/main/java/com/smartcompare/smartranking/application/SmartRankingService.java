package com.smartcompare.smartranking.application;

import com.smartcompare.product.domain.dto.EbayProductDTO;
import com.smartcompare.smartranking.domain.SmartRankingResult;
import com.smartcompare.smartranking.domain.dto.SmartRankingResultDTO;
import com.smartcompare.smartranking.infrastructure.SmartRankingResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmartRankingService {
    private final SmartRankingResultRepository smartRankingResultRepository;

    /**
     * Calcula el ranking inteligente de productos eBay y guarda el resultado.
     * @param searchTerms términos de búsqueda
     * @param userId id del usuario
     * @param products lista de productos eBay
     * @return DTO con el resultado del ranking
     */
    @Transactional
    public SmartRankingResultDTO analyzeAndSave(String searchTerms, Long userId, List<EbayProductDTO> products) {
        // Ranking simple: primero por condición (mejor), luego por precio (menor), luego por relevancia del título
        List<EbayProductDTO> ranked = products.stream()
                .sorted(Comparator
                        .comparing((EbayProductDTO p) -> getConditionWeight(p.getCondition())).reversed()
                        .thenComparing(EbayProductDTO::getPrice)
                        .thenComparing(p -> -relevanceScore(p.getTitle(), searchTerms)))
                .collect(Collectors.toList());

        List<String> topProductItemIds = ranked.stream().limit(3).map(EbayProductDTO::getItemId).collect(Collectors.toList());
        String justification = buildJustification(ranked, searchTerms);

        SmartRankingResult result = SmartRankingResult.builder()
                .searchTerms(searchTerms)
                .date(LocalDateTime.now())
                .userId(userId)
                .topProductItemIds(topProductItemIds)
                .justification(justification)
                .build();
        result = smartRankingResultRepository.save(result);
        return toDTO(result);
    }

    private int getConditionWeight(String condition) {
        if (condition == null) return 0;
        return switch (condition.toLowerCase()) {
            case "new" -> 4;
            case "open box", "like new" -> 3;
            case "very good - refurbished" -> 2;
            case "good - refurbished", "used" -> 1;
            default -> 0;
        };
    }

    private int relevanceScore(String title, String searchTerms) {
        if (title == null || searchTerms == null) return 0;
        String[] terms = searchTerms.toLowerCase().split(" ");
        int score = 0;
        for (String term : terms) {
            if (title.toLowerCase().contains(term)) score++;
        }
        return score;
    }

    private String buildJustification(List<EbayProductDTO> ranked, String searchTerms) {
        if (ranked.isEmpty()) return "No se encontraron productos para los términos: " + searchTerms;
        EbayProductDTO top = ranked.get(0);
        return "Mejor opción: '" + top.getTitle() + "' (" + top.getCondition() + ") por $" + top.getPrice() + ". Justificación: mejor condición y precio entre los resultados.";
    }

    private SmartRankingResultDTO toDTO(SmartRankingResult result) {
        return SmartRankingResultDTO.builder()
                .id(result.getId())
                .searchTerms(result.getSearchTerms())
                .date(result.getDate())
                .userId(result.getUserId())
                .topProductItemIds(result.getTopProductItemIds())
                .justification(result.getJustification())
                .build();
    }
}

