package com.smartcompare.product.infrastructure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class EbayOAuthService {
    @Value("${ebay.api.client-id}")
    private String clientId;

    @Value("${ebay.api.client-secret}")
    private String clientSecret;

    @Value("${ebay.api.token-url:https://api.ebay.com/identity/v1/oauth2/token}")
    private String tokenUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Data
    public static class EbayTokenResponse {
        private String access_token;
        private String token_type;
        private int expires_in;
        private String refresh_token;
    }

    public String getAppAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("scope", "https://api.ebay.com/oauth/api_scope");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<EbayTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, EbayTokenResponse.class);
        return response.getBody() != null ? response.getBody().getAccess_token() : null;
    }
}

