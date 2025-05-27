package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/searchhistory")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @GetMapping
    public ResponseEntity<List<SearchHistoryDTO>> getAll() {
        return ResponseEntity.ok(searchHistoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SearchHistoryDTO> create(@Validated @RequestBody SearchHistoryDTO dto) {
        return ResponseEntity.ok(searchHistoryService.create(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        searchHistoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

