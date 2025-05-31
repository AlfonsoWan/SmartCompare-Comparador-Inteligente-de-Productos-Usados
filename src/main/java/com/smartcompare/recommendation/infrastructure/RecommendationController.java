package com.smartcompare.recommendation.infrastructure;

import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.recommendation.domain.dto.RecommendationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.ok(recommendationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> create(@Validated @RequestBody RecommendationDTO dto) {
        return ResponseEntity.ok(recommendationService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
