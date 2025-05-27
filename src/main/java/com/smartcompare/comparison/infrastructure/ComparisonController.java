package com.smartcompare.comparison.infrastructure;

import com.smartcompare.comparison.application.ComparisonService;
import com.smartcompare.comparison.domain.dto.ComparisonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/comparisons")
@RequiredArgsConstructor
public class ComparisonController {
    private final ComparisonService comparisonService;

    @GetMapping
    public ResponseEntity<List<ComparisonDTO>> getAll() {
        return ResponseEntity.ok(comparisonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComparisonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comparisonService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ComparisonDTO> create(@Validated @RequestBody ComparisonDTO dto) {
        return ResponseEntity.ok(comparisonService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comparisonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

