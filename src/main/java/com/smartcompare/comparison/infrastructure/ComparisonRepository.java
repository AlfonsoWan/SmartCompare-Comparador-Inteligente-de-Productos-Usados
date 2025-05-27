package com.smartcompare.comparison.infrastructure;

import com.smartcompare.comparison.domain.Comparison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonRepository extends JpaRepository<Comparison, Long> {
}

