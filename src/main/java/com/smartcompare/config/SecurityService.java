package com.smartcompare.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    /**
     * Verifica si el usuario autenticado es el dueño del recurso (por userId) o es ADMIN.
     * @param resourceUserId El userId del recurso
     * @param authentication El objeto de autenticación
     * @return true si es dueño o admin
     */
    public boolean isOwnerOrAdmin(Long resourceUserId, Authentication authentication, String resourceType) {
        if (authentication == null || authentication.getName() == null) return false;
        // Si es admin
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        // Si es dueño
        try {
            Long authUserId = Long.parseLong(authentication.getName());
            return resourceUserId.equals(authUserId);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

