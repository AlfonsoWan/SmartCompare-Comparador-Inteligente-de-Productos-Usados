package com.smartcompare.searchhistory.application;

import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import com.smartcompare.searchhistory.domain.exception.SearchHistoryNotFoundException;
import com.smartcompare.searchhistory.domain.SearchHistory;
import com.smartcompare.searchhistory.infrastructure.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public SearchHistoryDTO createHistory(String terms, Long userId) {
        SearchHistory entity = searchHistoryRepository.save(
                SearchHistory.builder()
                        .terms(terms)
                        .userId(userId)
                        .build()
        );
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public SearchHistoryDTO findById(Long id) {
        SearchHistory entity = searchHistoryRepository.findById(id)
                .orElseThrow(() -> new SearchHistoryNotFoundException(id));
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<SearchHistoryDTO> findAll() {
        return searchHistoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SearchHistoryDTO toDTO(SearchHistory entity) {
        return SearchHistoryDTO.builder()
                .id(entity.getId())
                .terms(entity.getTerms())
                .date(entity.getDate())
                .userId(entity.getUserId())
                .build();
    }
}
