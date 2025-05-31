package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
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
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.ok(recommendationService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'recommendation')")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.findById(id));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<Page<RecommendationDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(recommendationService.findByUserIdPaged(userId, pageable));
    }

    @PostMapping
    @PreAuthorize("#dto.userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<RecommendationDTO> create(@Validated @RequestBody RecommendationDTO dto) {
        return ResponseEntity.ok(recommendationService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'recommendation')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
