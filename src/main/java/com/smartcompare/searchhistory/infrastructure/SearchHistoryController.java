package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/search-history")
@RequiredArgsConstructor
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("#userId == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<Page<SearchHistoryDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(searchHistoryService.findByUserIdPaged(userId, pageable));
    }

    @PostMapping
    @PreAuthorize("#authentication.name == T(java.lang.String).valueOf(#authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<SearchHistoryDTO> saveSearch(
            @RequestParam String terms,
            Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(searchHistoryService.save(terms, userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#id, authentication, 'searchhistory')")
    public ResponseEntity<SearchHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }
}
