package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.recommendation.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    // Permite buscar recomendaciones por usuario
    java.util.List<Recommendation> findByUserId(Long userId);

    Page<Recommendation> findByUserId(Long userId, Pageable pageable);
}
