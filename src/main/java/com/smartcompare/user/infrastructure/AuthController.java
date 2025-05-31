package com.smartcompare.user.infrastructure;

import com.smartcompare.user.application.UserService;
import com.smartcompare.user.domain.dto.AuthRequest;
import com.smartcompare.user.domain.dto.AuthResponse;
import com.smartcompare.user.domain.dto.UserDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerWithJwt(userDTO));
    }
}
