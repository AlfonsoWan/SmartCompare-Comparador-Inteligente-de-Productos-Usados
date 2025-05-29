package com.smartcompare.config.mercadolibre;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MercadoLibreAuthController {

    @Value("${mercadolibre.clientId}")
    private String clientId;

    @Value("${mercadolibre.redirectUri}")
    private String redirectUri;

    private final MercadoLibreOAuthService oauthService;

    public MercadoLibreAuthController(MercadoLibreOAuthService oauthService) {
        this.oauthService = oauthService;
    }

    @GetMapping("/auth/mercadolibre/login")
    public void login(HttpServletResponse response) throws IOException {
        String authUrl = "https://auth.mercadolibre.com.ar/authorization?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

        response.sendRedirect(authUrl);
    }

    @GetMapping("/auth/mercadolibre/callback")
    public ResponseEntity<String> callback(@RequestParam("code") String code) {
        TokenResponse token = oauthService.exchangeCodeForToken(code);
        // Guardar token en base de datos o sesión según tu lógica
        return ResponseEntity.ok("Token obtenido: " + token.getAccess_token());
    }
}
