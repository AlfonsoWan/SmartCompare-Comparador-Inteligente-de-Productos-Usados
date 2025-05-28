package com.smartcompare.favorite.infrastructure;

import com.smartcompare.favorite.application.FavoriteService;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAll() {
        return ResponseEntity.ok(favoriteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> create(@Validated @RequestBody FavoriteDTO dto) {
        return ResponseEntity.ok(favoriteService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}