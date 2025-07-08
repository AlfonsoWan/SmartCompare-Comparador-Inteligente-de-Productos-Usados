package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import com.smartcompare.config.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final SecurityService securityService;

    /**
     * Recomendaciones personalizadas (hasta 5) para el usuario autenticado.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecommendationDTO>> getForUser(Authentication auth) {
        Long userId = securityService.getAuthenticatedUserId(auth);
        return ResponseEntity.ok(recommendationService.getRecommendations(userId));
    }

    /**
     * Listar todas las recomendaciones (solo ADMIN).
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.ok(recommendationService.findAll());
    }

    /**
     * Obtener una recomendación por su ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("@securityService.canAccessResource(#id, 'recommendation', authentication) or hasRole('ADMIN')")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.findById(id));
    }

    /**
     * Obtener recomendaciones persistidas paginadas de un usuario (solo ADMIN o el mismo usuario).
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("@securityService.isSameUser(#userId, authentication) or hasRole('ADMIN')")
    public ResponseEntity<Page<RecommendationDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(recommendationService.findByUserIdPaged(userId, pageable));
    }

    /**
     * Crear una nueva recomendación (para pruebas o administración).
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecommendationDTO> create(
            @Validated @RequestBody RecommendationDTO dto,
            Authentication auth) {
        Long userId = securityService.getAuthenticatedUserId(auth);
        dto.setUserId(userId);
        return ResponseEntity.ok(recommendationService.create(dto));
    }

    /**
     * Borrar una recomendación por ID.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.canAccessResource(#id, 'recommendation', authentication) or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}