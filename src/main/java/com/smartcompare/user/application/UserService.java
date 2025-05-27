package com.smartcompare.user.application;

import com.smartcompare.user.domain.User;
import com.smartcompare.user.domain.dto.AuthRequest;
import com.smartcompare.user.domain.dto.AuthResponse;
import com.smartcompare.user.domain.dto.UserDTO;
import com.smartcompare.user.infrastructure.JwtService;
import com.smartcompare.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDTO register(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .authType(dto.getAuthType())
                .role(User.Role.USER)
                .build();

        user = userRepository.save(user);
        return toDTO(user);
    }

    @Transactional
    public AuthResponse registerWithJwt(UserDTO dto) {
        UserDTO registeredUser = register(dto);
        String token = jwtService.generateToken(userRepository.findByEmail(dto.getEmail()).get());
        return AuthResponse.builder()
                .token(token)
                .user(registeredUser)
                .build();
    }

    @Transactional
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .user(toDTO(user))
                .build();
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
}
