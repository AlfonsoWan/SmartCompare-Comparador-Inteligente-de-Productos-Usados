package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByPrimaryCategoryIdAndIdNotIn(
            String categoryId,
            List<String> excludeIds,
            Pageable pageable
    );
}
