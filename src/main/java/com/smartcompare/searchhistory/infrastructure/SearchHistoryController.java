package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.config.SecurityService;
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
    private final SecurityService securityService; // Reemplazar UserService

    @GetMapping("/user/{userId}")
    @PreAuthorize("@securityService.isSameUser(#userId, authentication) or hasRole('ADMIN')")
    public ResponseEntity<Page<SearchHistoryDTO>> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(searchHistoryService.findByUserIdPaged(userId, pageable));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()") // Solo verifica autenticación básica
    public ResponseEntity<SearchHistoryDTO> saveSearch(
            @RequestParam String terms,
            Authentication authentication) {
        // Obtiene el ID del usuario autenticado directamente
        Long userId = securityService.getAuthenticatedUserId(authentication);
        return ResponseEntity.ok(searchHistoryService.save(terms, userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.canAccessResource(#id, 'comparison', authentication)")
    public ResponseEntity<SearchHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }
}
