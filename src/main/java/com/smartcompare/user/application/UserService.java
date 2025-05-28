package com.smartcompare.user.application;

import com.smartcompare.user.domain.User;
import com.smartcompare.user.domain.dto.UserDTO;
import com.smartcompare.user.infrastructure.UserRepository;
import com.smartcompare.user.domain.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.smartcompare.user.domain.dto.AuthRequest;
import com.smartcompare.user.domain.dto.AuthResponse;
import com.smartcompare.user.infrastructure.JwtService;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build()
        );
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse registerWithJwt(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new InvalidCredentialsException("El email ya está registrado");
        }
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .authType("LOCAL")
                .role(userDTO.getRole() != null ? userDTO.getRole() : "USER")
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build()
        );
        return new AuthResponse(token);
    }

    @Transactional
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new InvalidCredentialsException("El email ya está registrado");
        }
        User user = User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .authType("LOCAL")
                .role(userDTO.getRole() != null ? userDTO.getRole() : "USER")
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(user);
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .authType(user.getAuthType())
                .role(user.getRole())
                .build();
    }

    public java.util.Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}