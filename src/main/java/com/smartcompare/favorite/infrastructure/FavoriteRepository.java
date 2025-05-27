package com.smartcompare.favorite.infrastructure;

import com.smartcompare.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}

