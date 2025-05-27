package com.smartcompare.comparison.application;

import com.smartcompare.comparison.domain.Comparison;
import com.smartcompare.comparison.domain.dto.ComparisonDTO;
import com.smartcompare.comparison.domain.exception.ComparisonNotFoundException;
import com.smartcompare.comparison.infrastructure.ComparisonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComparisonServiceTest {
    @Mock
    private ComparisonRepository comparisonRepository;
    @InjectMocks
    private ComparisonService comparisonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comparisonService = new ComparisonService(comparisonRepository);
    }

    @Test
    void testFindById_found() {
        Comparison comparison = Comparison.builder().id(1L).productIds(Arrays.asList(1L,2L)).date(LocalDateTime.now()).userId(1L).build();
        when(comparisonRepository.findById(1L)).thenReturn(Optional.of(comparison));
        ComparisonDTO dto = comparisonService.findById(1L);
        assertEquals(1L, dto.getId());
        assertEquals(2, dto.getProductIds().size());
    }

    @Test
    void testFindById_notFound() {
        when(comparisonRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ComparisonNotFoundException.class, () -> comparisonService.findById(2L));
    }

    @Test
    void testFindAll() {
        Comparison comparison1 = Comparison.builder().id(1L).productIds(Arrays.asList(1L,2L)).date(LocalDateTime.now()).userId(1L).build();
        Comparison comparison2 = Comparison.builder().id(2L).productIds(Arrays.asList(3L,4L)).date(LocalDateTime.now()).userId(2L).build();
        when(comparisonRepository.findAll()).thenReturn(Arrays.asList(comparison1, comparison2));
        List<ComparisonDTO> list = comparisonService.findAll();
        assertEquals(2, list.size());
    }
}

