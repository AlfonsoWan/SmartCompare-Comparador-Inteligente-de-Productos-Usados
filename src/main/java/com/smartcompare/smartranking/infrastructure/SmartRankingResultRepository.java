package com.smartcompare.smartranking.infrastructure;

import com.smartcompare.smartranking.domain.SmartRankingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmartRankingResultRepository extends JpaRepository<SmartRankingResult, Long> {
}

