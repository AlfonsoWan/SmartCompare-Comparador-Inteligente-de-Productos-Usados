package com.smartcompare.favorite.infrastructure;

import com.smartcompare.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Page<Favorite> findByUserId(Long userId, Pageable pageable);
}

