package com.smartcompare.user.infrastructure;

import com.smartcompare.user.application.UserService;
import com.smartcompare.user.domain.dto.UserDTO;
import com.smartcompare.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .authType(user.getAuthType())
                .role(user.getRole())
                .build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@Validated @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }
}
