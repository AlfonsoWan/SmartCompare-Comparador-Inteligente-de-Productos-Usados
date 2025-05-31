package com.smartcompare.user.application;

import com.smartcompare.user.domain.User;
import com.smartcompare.user.domain.dto.AuthRequest;
import com.smartcompare.user.domain.dto.AuthResponse;
import com.smartcompare.user.domain.dto.UserDTO;
import com.smartcompare.user.infrastructure.JwtService;
import com.smartcompare.user.infrastructure.UserRepository;
import com.smartcompare.email.domain.EmailService;        // <-- Importamos EmailService
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;   // <-- Inyectamos EmailService

    @Transactional
    public UserDTO register(UserDTO dto) {
        // 1) Creamos y persistimos el nuevo usuario (igual que antes)
        User userToSave = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .authType(dto.getAuthType())
                .build();

        User savedUser = userRepository.save(userToSave);

        // 2) Preparamos el Contexto para la plantilla Thymeleaf
        Context context = new Context();
        context.setVariable("name", savedUser.getName());
        // Por ejemplo, enviamos la fecha de registro (puedes formatear como quieras)
        String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        context.setVariable("date", fechaActual);
        // Si quieres enviar un link de confirmación, podrías agregar algo así:
        // context.setVariable("confirmUrl", "https://tu-dominio.com/confirm?token=XXXXX");

        // 3) Envío de correo de bienvenida
        // Usamos la plantilla "email/confirmation" (solamente si la has copiado igual que en el otro proyecto)
        // El Subject puede ser: "Bienvenido a SmartCompare"
        emailService.sendHtmlEmail(
                savedUser.getEmail(),
                "Bienvenido a SmartCompare",
                "email/confirmation",  // Apunta a src/main/resources/templates/email/confirmation.html
                context
        );

        // 4) Retornamos el DTO del usuario recién creado
        return toDTO(savedUser);
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
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        User user = userOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return AuthResponse.builder()
                .token(jwtService.generateToken(user))
                .user(toDTO(user))
                .build();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
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
