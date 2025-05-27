package com.smartcompare.recommendation.application;

import com.smartcompare.recommendation.domain.Recommendation;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import com.smartcompare.recommendation.domain.exception.RecommendationNotFoundException;
import com.smartcompare.recommendation.infrastructure.RecommendationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {
    @Mock
    private RecommendationRepository recommendationRepository;
    @InjectMocks
    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(recommendationRepository);
    }

    @Test
    void testFindById_found() {
        Recommendation rec = Recommendation.builder().id(1L).suggestedProductId(10L).reason("test").userId(1L).build();
        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(rec));
        RecommendationDTO dto = recommendationService.findById(1L);
        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getSuggestedProductId());
    }

    @Test
    void testFindById_notFound() {
        when(recommendationRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RecommendationNotFoundException.class, () -> recommendationService.findById(2L));
    }

    @Test
    void testFindAll() {
        Recommendation r1 = Recommendation.builder().id(1L).suggestedProductId(10L).reason("a").userId(1L).build();
        Recommendation r2 = Recommendation.builder().id(2L).suggestedProductId(20L).reason("b").userId(2L).build();
        when(recommendationRepository.findAll()).thenReturn(Arrays.asList(r1, r2));
        List<RecommendationDTO> list = recommendationService.findAll();
        assertEquals(2, list.size());
    }
}

