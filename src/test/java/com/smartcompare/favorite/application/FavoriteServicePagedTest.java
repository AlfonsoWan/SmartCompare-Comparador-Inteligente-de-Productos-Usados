package com.smartcompare.favorite.application;

import com.smartcompare.favorite.domain.Favorite;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.favorite.infrastructure.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteServicePagedTest {
    @Mock
    private FavoriteRepository favoriteRepository;
    @InjectMocks
    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        favoriteService = new FavoriteService(favoriteRepository);
    }

    @Test
    void testFindByUserIdPaged() {
        Long userId = 1L;
        Favorite fav1 = Favorite.builder().id(1L).productId(10L).userId(userId).savedDate(LocalDateTime.now()).build();
        Favorite fav2 = Favorite.builder().id(2L).productId(20L).userId(userId).savedDate(LocalDateTime.now()).build();
        List<Favorite> favList = Arrays.asList(fav1, fav2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Favorite> favPage = new PageImpl<>(favList, pageable, favList.size());
        when(favoriteRepository.findByUserId(userId, pageable)).thenReturn(favPage);
        Page<FavoriteDTO> result = favoriteService.findByUserIdPaged(userId, pageable);
        assertEquals(2, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }
}

