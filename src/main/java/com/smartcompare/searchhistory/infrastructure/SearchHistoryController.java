package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SearchHistoryDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(searchHistoryService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<SearchHistoryDTO> saveSearch(
            @RequestParam String terms,
            Authentication authentication) {
        // Asumiendo que el ID del usuario está almacenado en el principal
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(searchHistoryService.save(terms, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }
}
