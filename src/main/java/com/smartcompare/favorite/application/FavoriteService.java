package com.smartcompare.favorite.application;

import com.smartcompare.favorite.domain.Favorite;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.favorite.domain.exception.FavoriteNotFoundException;
import com.smartcompare.favorite.infrastructure.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Transactional(readOnly = true)
    public FavoriteDTO findById(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new FavoriteNotFoundException("Favorito no encontrado: " + id));
        return toDTO(favorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteDTO> findAll() {
        return favoriteRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public FavoriteDTO create(FavoriteDTO dto) {
        Favorite favorite = Favorite.builder()
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .savedDate(LocalDateTime.now())
                .build();
        Favorite saved = favoriteRepository.save(favorite);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new FavoriteNotFoundException("Favorito no encontrado: " + id);
        }
        favoriteRepository.deleteById(id);
    }

    private FavoriteDTO toDTO(Favorite favorite) {
        return FavoriteDTO.builder()
                .id(favorite.getId())
                .productId(favorite.getProductId())
                .userId(favorite.getUserId())
                .savedDate(favorite.getSavedDate())
                .build();
    }
}