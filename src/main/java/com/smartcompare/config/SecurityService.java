package com.smartcompare.config;

import com.smartcompare.user.application.UserService;
import com.smartcompare.comparison.application.ComparisonService;
import com.smartcompare.favorite.application.FavoriteService;
import com.smartcompare.recommendation.application.RecommendationService;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final ComparisonService comparisonService;
    private final RecommendationService recommendationService;
    private final SearchHistoryService searchHistoryService;

    public boolean isOwnerOrAdmin(Long resourceUserId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        // Check for admin role
        if (hasAdminRole(authentication)) {
            return true;
        }

        // Check ownership
        return isSameUser(resourceUserId, authentication);
    }

    public boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }

    public boolean isSameUser(Long userId, Authentication authentication) {
        return getCurrentUserId(authentication)
                .map(id -> id.equals(userId))
                .orElse(false);
    }

    public Optional<Long> getCurrentUserId(Authentication authentication) {
        try {
            // Try JWT token case where principal is the username/email
            String username = authentication.getName();
            return userService.findByEmail(username).map(User::getId);
        } catch (ClassCastException e) {
            // Fallback for other authentication types
            try {
                if (authentication.getPrincipal() instanceof User) {
                    return Optional.of(((User) authentication.getPrincipal()).getId());
                }
                return Optional.empty();
            } catch (Exception ex) {
                return Optional.empty();
            }
        }
    }

    public User getCurrentUser(Authentication authentication) {
        return userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return getCurrentUserId(authentication)
                .orElseThrow(() -> new RuntimeException("Usuario no autenticado"));
    }

    public boolean canAccessResource(Long resourceId, String resourceType, Authentication authentication) {
        if (hasAdminRole(authentication)) {
            return true;
        }

        Long resourceUserId = getResourceUserId(resourceId, resourceType);
        return isSameUser(resourceUserId, authentication);
    }

    private Long getResourceUserId(Long resourceId, String resourceType) {
        switch (resourceType.toLowerCase()) {
            case "favorite":
                return favoriteService.getUserIdFromFavorite(resourceId);
            case "comparison":
                return comparisonService.getUserIdFromComparison(resourceId);
            case "recommendation":
                return recommendationService.getUserIdFromRecommendation(resourceId);
            case "searchhistory":
                return searchHistoryService.getUserIdFromSearchHistory(resourceId);
            default:
                throw new IllegalArgumentException("Tipo de recurso no soportado: " + resourceType);
        }
    }
}