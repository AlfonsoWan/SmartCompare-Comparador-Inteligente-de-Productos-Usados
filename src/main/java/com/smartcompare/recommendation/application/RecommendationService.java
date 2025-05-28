package com.smartcompare.recommendation.application;

import com.smartcompare.recommendation.domain.Recommendation;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import com.smartcompare.recommendation.domain.exception.RecommendationNotFoundException;
import com.smartcompare.recommendation.infrastructure.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

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
}