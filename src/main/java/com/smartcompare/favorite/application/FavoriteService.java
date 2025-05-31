package com.smartcompare.favorite.application;

import com.smartcompare.favorite.domain.Favorite;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import com.smartcompare.favorite.domain.exception.FavoriteNotFoundException;
import com.smartcompare.favorite.infrastructure.FavoriteRepository;
import com.smartcompare.product.domain.Product;
import com.smartcompare.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    @Autowired
    private ProductRepository productRepository;

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

    @Transactional(readOnly = true)
    public List<FavoriteDTO> findByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public FavoriteDTO create(FavoriteDTO dto) {
        Long productId = dto.getProductId();
        // Si no se provee productId, intentamos buscar o crear el producto por ebayItemId o url
        if (productId == null) {
            Product product = null;
            if (dto.getUrl() != null) {
                product = productRepository.findAll().stream()
                        .filter(p -> p.getUrl().equals(dto.getUrl()))
                        .findFirst().orElse(null);
            }
            if (product == null && dto.getTitle() != null) {
                product = Product.builder()
                        .name(dto.getTitle())
                        .price(dto.getPrice())
                        .image(dto.getImage())
                        .source("EBAY")
                        .url(dto.getUrl())
                        .build();
                product = productRepository.save(product);
            }
            if (product != null) {
                productId = product.getId();
            } else {
                throw new RuntimeException("No se pudo crear o encontrar el producto para el favorito");
            }
        }
        Favorite favorite = Favorite.builder()
                .productId(productId)
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

