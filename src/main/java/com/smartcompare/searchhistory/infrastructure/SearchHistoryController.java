package com.smartcompare.searchhistory.infrastructure;

import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.searchhistory.domain.dto.SearchHistoryDTO;
import com.smartcompare.user.application.UserService;
import com.smartcompare.user.domain.User;
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
    private final UserService userService;

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
    @PreAuthorize("@securityService.isSameUser(#userId, authentication) or hasRole('ADMIN')")
    public ResponseEntity<SearchHistoryDTO> saveSearch(
            @RequestParam String terms,
            Authentication authentication) {
        // Obtener el email/username autenticado
        String email = authentication.getName();
        // Buscar el usuario por email
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Long userId = user.getId();
        return ResponseEntity.ok(searchHistoryService.save(terms, userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.isSameUser(#userId, authentication) or hasRole('ADMIN')")
    public ResponseEntity<SearchHistoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(searchHistoryService.findById(id));
    }
}
