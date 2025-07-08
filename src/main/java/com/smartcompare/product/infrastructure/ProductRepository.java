package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByPrimaryCategoryIdAndIdNotIn(
            String categoryId,
            List<String> excludeIds,
            Pageable pageable
    );
    @Query("""
      SELECT p
      FROM Product p
      WHERE LOWER(p.name) LIKE %:keyword%
        AND p.id NOT IN :excludeIds
      ORDER BY LENGTH(p.name) ASC
      """)
    List<Product> findSimilarByNameKeyword(
            @Param("keyword") String keyword,
            @Param("excludeIds") List<String> excludeIds,
            Pageable pageable
    );
}

