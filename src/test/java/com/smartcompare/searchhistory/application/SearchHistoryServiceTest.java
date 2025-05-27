package com.smartcompare.searchhistory.application;

import com.smartcompare.searchhistory.domain.SearchHistory;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import com.smartcompare.searchhistory.domain.exception.SearchHistoryNotFoundException;
import com.smartcompare.searchhistory.infrastructure.SearchHistoryRepository;
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

class SearchHistoryServiceTest {
    @Mock
    private SearchHistoryRepository searchHistoryRepository;
    @InjectMocks
    private SearchHistoryService searchHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchHistoryService = new SearchHistoryService(searchHistoryRepository);
    }

    @Test
    void testFindById_found() {
        SearchHistory history = SearchHistory.builder().id(1L).terms("laptop").date(LocalDateTime.now()).userId(1L).build();
        when(searchHistoryRepository.findById(1L)).thenReturn(Optional.of(history));
        SearchHistoryDTO dto = searchHistoryService.findById(1L);
        assertEquals(1L, dto.getId());
        assertEquals("laptop", dto.getTerms());
    }

    @Test
    void testFindById_notFound() {
        when(searchHistoryRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(SearchHistoryNotFoundException.class, () -> searchHistoryService.findById(2L));
    }

    @Test
    void testFindAll() {
        SearchHistory h1 = SearchHistory.builder().id(1L).terms("laptop").date(LocalDateTime.now()).userId(1L).build();
        SearchHistory h2 = SearchHistory.builder().id(2L).terms("phone").date(LocalDateTime.now()).userId(2L).build();
        when(searchHistoryRepository.findAll()).thenReturn(Arrays.asList(h1, h2));
        List<SearchHistoryDTO> list = searchHistoryService.findAll();
        assertEquals(2, list.size());
    }
}

