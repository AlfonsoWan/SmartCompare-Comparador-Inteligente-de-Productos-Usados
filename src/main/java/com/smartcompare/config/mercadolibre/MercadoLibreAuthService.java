package com.smartcompare.config.mercadolibre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.Instant;
import java.util.Collections;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MercadoLibreAuthService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${mercadolibre.app-id}")
    private String appId;

    @Value("${mercadolibre.client-secret}")
    private String clientSecret;

    @Value("${mercadolibre.refresh-token}")
    private String refreshToken;

    @Autowired
    private MercadoLibreTokenConfigRepository tokenConfigRepository;

    // Método para obtener el refresh token desde la base de datos o properties
    private String getRefreshToken() {
        MercadoLibreTokenConfig config = tokenConfigRepository.findById("mercadolibre").orElse(null);
        if (config == null) {
            // Si no existe en BD, usar el de properties y guardarlo
            config = new MercadoLibreTokenConfig();
            config.setRefreshToken(refreshToken);
            tokenConfigRepository.save(config);
        }
        return config.getRefreshToken();
    }

    // Método para actualizar el refresh token en la base de datos
    private void updateRefreshToken(String newRefreshToken) {
        MercadoLibreTokenConfig config = tokenConfigRepository.findById("mercadolibre").orElse(new MercadoLibreTokenConfig());
        config.setRefreshToken(newRefreshToken);
        tokenConfigRepository.save(config);
    }

    // Método para obtener el access token desde la API de MercadoLibre
    public String getAccessToken() {
        String currentRefreshToken = getRefreshToken();
        String url = "https://api.mercadolibre.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", appId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token", currentRefreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode json = objectMapper.readTree(response.getBody());
                String accessToken = json.get("access_token").asText();
                String newRefreshToken = json.has("refresh_token") ? json.get("refresh_token").asText() : null;
                if (newRefreshToken != null && !newRefreshToken.equals(currentRefreshToken)) {
                    updateRefreshToken(newRefreshToken);
                }
                return accessToken;
            } catch (Exception e) {
                throw new RuntimeException("Error al parsear la respuesta de MercadoLibre", e);
            }
        } else {
            throw new RuntimeException("Error al obtener access token de MercadoLibre: " + response.getStatusCode());
        }
    }

    // Ejemplo de uso en un método de autenticación
    public void authenticate() {
        String currentRefreshToken = getRefreshToken();
        // ... lógica para solicitar nuevo access token usando currentRefreshToken ...
        // Cuando recibas un nuevo refresh token de la API:
        // updateRefreshToken(nuevoRefreshToken);
    }
}
