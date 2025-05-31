package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserId(Long userId);
    Page<SearchHistory> findByUserId(Long userId, Pageable pageable);
}
