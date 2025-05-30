package com.smartcompare.config.mercadolibre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

@Component
public class MercadoLibreAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${mercadolibre.app-id}")
    private String appId;

    @Value("${mercadolibre.client-secret}")
    private String clientSecret;

    @Value("${mercadolibre.redirect-uri}")
    private String redirectUri;

    public String generarUrlAutorizacion() {
        String url = "https://auth.mercadolibre.com.ar/authorization"
                + "?response_type=code"
                + "&client_id=" + appId
                + "&redirect_uri=" + redirectUri;
        return url;
    }

    public String obtenerToken(String code, String codeVerifier) {
        String url = "https://api.mercadolibre.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", appId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("redirect_uri", redirectUri);
        if (codeVerifier != null) {
            body.add("code_verifier", codeVerifier);
        }

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response.getBody();
    }
}
