package com.smartcompare.config.mercadolibre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class    MercadoLibreOAuthService {

    @Value("${mercadolibre.clientId}")
    private String clientId;

    @Value("${mercadolibre.clientSecret}")
    private String clientSecret;

    @Value("${mercadolibre.redirectUri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public TokenResponse exchangeCodeForToken(String code) {
        String tokenUrl = "https://api.mercadolibre.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
    }
}