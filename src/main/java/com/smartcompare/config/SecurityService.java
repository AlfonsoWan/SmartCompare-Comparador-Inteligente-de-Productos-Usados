package com.smartcompare.config;

import com.smartcompare.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityService {
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
            Long authUserId = ((User) authentication.getPrincipal()).getId();
            return resourceUserId.equals(authUserId);
        } catch (Exception e) {
            return false;
        }
    }
}

