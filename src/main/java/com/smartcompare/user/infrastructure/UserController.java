package com.smartcompare.user.infrastructure;

import com.smartcompare.user.application.UserService;
import com.smartcompare.user.domain.dto.UserDTO;
import com.smartcompare.user.domain.User;
import com.smartcompare.user.domain.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Obtiene un usuario por email.
     */
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

    /**
     * Registra un nuevo usuario.
     */
    @PostMapping
    public ResponseEntity<UserDTO> register(@Validated @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.register(dto));
    }

    /**
     * Endpoints para gestión de direcciones del usuario autenticado
     */
    @GetMapping("/me/addresses")
    public ResponseEntity<List<Address>> getMyAddresses() {
        return ResponseEntity.ok(userService.getMyAddresses());
    }

    @PostMapping("/me/addresses")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        return ResponseEntity.ok(userService.addAddress(address));
    }

    @DeleteMapping("/me/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        userService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}
