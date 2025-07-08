package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.config.SecurityService;
import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final SecurityService securityService;

    /**
     * Recomendaciones “en vivo” (hasta 5) para el usuario autenticado.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RecommendationDTO>> getForUser(Authentication auth) {
        Long userId = securityService.getAuthenticatedUserId(auth);
        List<RecommendationDTO> recs = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(recs);
    }

    /**
     * Listar todas las recomendaciones persistidas (solo ADMIN, para pruebas).
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
        RecommendationDTO dto = recommendationService.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Obtener recomendaciones persistidas paginadas de un usuario
     * (solo ADMIN o el propio usuario).
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("@securityService.isSameUser(#userId, authentication) or hasRole('ADMIN')")
    public ResponseEntity<Page<RecommendationDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecommendationDTO> pageDto = recommendationService.findByUserIdPaged(userId, pageable);
        return ResponseEntity.ok(pageDto);
    }

    /**
     * Crear una nueva recomendación (para pruebas o administración).
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RecommendationDTO> create(
            @Validated @RequestBody RecommendationDTO dto,
            Authentication auth
    ) {
        Long userId = securityService.getAuthenticatedUserId(auth);
        dto.setUserId(userId);
        RecommendationDTO created = recommendationService.create(dto);
        return ResponseEntity.ok(created);
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
