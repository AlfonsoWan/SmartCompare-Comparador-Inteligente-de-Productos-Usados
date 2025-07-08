package com.smartcompare.searchhistory.application;

import com.smartcompare.searchhistory.domain.SearchHistory;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import com.smartcompare.searchhistory.domain.exception.SearchHistoryNotFoundException;
import com.smartcompare.searchhistory.infrastructure.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<SearchHistoryDTO> findAll() {
        return searchHistoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SearchHistoryDTO findById(Long id) {
        return searchHistoryRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new SearchHistoryNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<SearchHistoryDTO> findByUserId(Long userId) {
        return searchHistoryRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SearchHistoryDTO> findByUserIdPaged(Long userId, Pageable pageable) {
        return searchHistoryRepository.findByUserId(userId, pageable)
                .map(this::toDTO);
    }

    @Transactional
    public SearchHistoryDTO save(String terms, Long userId) {
        SearchHistory entity = SearchHistory.builder()
                .terms(terms)
                .date(LocalDateTime.now())
                .userId(userId)
                .build();
        return toDTO(searchHistoryRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Long getUserIdFromSearchHistory(Long searchHistoryId) {
        return findById(searchHistoryId).getUserId();
    }

    /**
     * Nuevo método → devuelve solo los términos de búsqueda de un usuario,
     * en el mismo orden en que los guardaste (o el orden definido en el repo).
     */
    @Transactional(readOnly = true)
    public List<String> getSearchHistoryTerms(Long userId) {
        return searchHistoryRepository.findByUserId(userId).stream()
                .map(SearchHistory::getTerms)
                .distinct()
                .collect(Collectors.toList());
    }

    private SearchHistoryDTO toDTO(SearchHistory entity) {
        return SearchHistoryDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .terms(entity.getTerms())
                .date(entity.getDate())
                .build();
    }
}
