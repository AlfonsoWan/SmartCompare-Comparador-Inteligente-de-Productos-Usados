package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
}

