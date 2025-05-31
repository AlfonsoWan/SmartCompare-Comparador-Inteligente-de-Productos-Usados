package com.smartcompare.comparison.infrastructure;

import com.smartcompare.comparison.application.ComparisonService;
import com.smartcompare.comparison.domain.dto.ComparisonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/comparisons")
@RequiredArgsConstructor
public class    ComparisonController {
    private final ComparisonService comparisonService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComparisonDTO>> getAll() {
        return ResponseEntity.ok(comparisonService.findAll());
    }

    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ComparisonDTO>> getAllPaged(Pageable pageable) {
        return ResponseEntity.ok(comparisonService.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'comparison')")
    public ResponseEntity<ComparisonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comparisonService.findById(id));
    }

    @PostMapping
    @PreAuthorize("#dto.userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<ComparisonDTO> create(@Validated @RequestBody ComparisonDTO dto, Authentication authentication) {
        return ResponseEntity.ok(comparisonService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'comparison')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comparisonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

