package com.smartcompare.searchhistory.application;

import com.smartcompare.searchhistory.domain.SearchHistory;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import com.smartcompare.searchhistory.domain.exception.SearchHistoryNotFoundException;
import com.smartcompare.searchhistory.infrastructure.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional(readOnly = true)
    public SearchHistoryDTO findById(Long id) {
        SearchHistory searchHistory = searchHistoryRepository.findById(id)
                .orElseThrow(() -> new SearchHistoryNotFoundException("Historial no encontrado: " + id));
        return toDTO(searchHistory);
    }

    @Transactional(readOnly = true)
    public List<SearchHistoryDTO> findAll() {
        return searchHistoryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public SearchHistoryDTO create(SearchHistoryDTO dto) {
        SearchHistory searchHistory = SearchHistory.builder()
                .terms(dto.getTerms())
                .date(LocalDateTime.now())
                .userId(dto.getUserId())
                .build();
        SearchHistory saved = searchHistoryRepository.save(searchHistory);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!searchHistoryRepository.existsById(id)) {
            throw new SearchHistoryNotFoundException("Historial no encontrado: " + id);
        }
        searchHistoryRepository.deleteById(id);
    }

    private SearchHistoryDTO toDTO(SearchHistory searchHistory) {
        return SearchHistoryDTO.builder()
                .id(searchHistory.getId())
                .terms(searchHistory.getTerms())
                .date(searchHistory.getDate())
                .userId(searchHistory.getUserId())
                .build();
    }
}

