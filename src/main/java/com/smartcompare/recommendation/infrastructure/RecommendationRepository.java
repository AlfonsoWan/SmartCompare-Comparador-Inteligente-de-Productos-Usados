package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.recommendation.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}

