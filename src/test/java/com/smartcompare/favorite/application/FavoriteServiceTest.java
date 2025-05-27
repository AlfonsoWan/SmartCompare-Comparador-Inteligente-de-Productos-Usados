package com.smartcompare.favorite.application;

import com.smartcompare.favorite.domain.Favorite;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.favorite.domain.exception.FavoriteNotFoundException;
import com.smartcompare.favorite.infrastructure.FavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteServiceTest {
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
    void testFindById_found() {
        Favorite favorite = Favorite.builder().id(1L).productId(10L).userId(1L).savedDate(LocalDateTime.now()).build();
        when(favoriteRepository.findById(1L)).thenReturn(Optional.of(favorite));
        FavoriteDTO dto = favoriteService.findById(1L);
        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getProductId());
    }

    @Test
    void testFindById_notFound() {
        when(favoriteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.findById(2L));
    }

    @Test
    void testFindAll() {
        Favorite favorite1 = Favorite.builder().id(1L).productId(10L).userId(1L).savedDate(LocalDateTime.now()).build();
        Favorite favorite2 = Favorite.builder().id(2L).productId(20L).userId(2L).savedDate(LocalDateTime.now()).build();
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(favorite1, favorite2));
        List<FavoriteDTO> list = favoriteService.findAll();
        assertEquals(2, list.size());
    }
}

