package com.smartcompare.favorite.infrastructure;

import com.smartcompare.favorite.application.FavoriteService;
import com.smartcompare.favorite.domain.dto.FavoriteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FavoriteDTO>> getAll() {
        return ResponseEntity.ok(favoriteService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'favorite')")
    public ResponseEntity<FavoriteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(favoriteService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<Page<FavoriteDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(favoriteService.findByUserIdPaged(userId, pageable));
    }

    @PostMapping
    @PreAuthorize("#dto.userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<FavoriteDTO> create(@Validated @RequestBody FavoriteDTO dto) {
        return ResponseEntity.ok(favoriteService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'favorite')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

