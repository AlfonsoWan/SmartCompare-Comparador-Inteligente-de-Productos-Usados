package com.smartcompare.config.mercadolibre.token;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;

@Service
public class MercadoLibreTokenService {

    private static final Logger logger = LoggerFactory.getLogger(MercadoLibreTokenService.class);
    private final MercadoLibreTokenRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mercadolibre.app-id}")
    private String appId;

    @Value("${mercadolibre.client-secret}")
    private String clientSecret;

    public MercadoLibreTokenService(MercadoLibreTokenRepository repository) {
        this.repository = repository;
    }

    public String getValidAccessToken() {
        MercadoLibreToken token = repository.findTopByOrderByIdDesc();
        if (token == null || token.getExpirationTime() == null || token.getExpirationTime().isBefore(Instant.now())) {
            logger.info("El token ha expirado o no existe, refrescando...");
            refreshToken();
            token = repository.findTopByOrderByIdDesc();
        }
        return token != null ? token.getAccessToken() : null;
    }

    public void saveToken(String tokenResponse) {
        try {
            JsonNode json = objectMapper.readTree(tokenResponse);
            MercadoLibreToken token = new MercadoLibreToken();
            token.setAccessToken(json.get("access_token").asText());
            token.setRefreshToken(json.get("refresh_token").asText());
            token.setTokenType(json.has("token_type") ? json.get("token_type").asText() : null);
            token.setExpiresIn(json.has("expires_in") ? (int) json.get("expires_in").asLong() : null);
            if (token.getExpiresIn() != null) {
                token.setExpirationTime(Instant.now().plusSeconds(token.getExpiresIn()));
            }
            repository.save(token);
            logger.info("Token guardado correctamente: {}", token);
        } catch (Exception e) {
            logger.error("Error al guardar el token de Mercado Libre", e);
            throw new RuntimeException("Error al guardar el token de Mercado Libre", e);
        }
    }

    public String getAccessToken() {
        MercadoLibreToken token = repository.findTopByOrderByIdDesc();
        return token != null ? token.getAccessToken() : null;
    }

    public void refreshToken() {
        MercadoLibreToken token = repository.findTopByOrderByIdDesc();
        if (token == null || token.getRefreshToken() == null) {
            throw new RuntimeException("No se encontró un token válido para refrescar.");
        }

        String url = "https://api.mercadolibre.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", appId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", token.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                saveToken(response.getBody());
            } else {
                throw new RuntimeException("Error al refrescar el token: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Error al refrescar el token de Mercado Libre", e);
            throw new RuntimeException("Error al refrescar el token de Mercado Libre", e);
        }
    }
}