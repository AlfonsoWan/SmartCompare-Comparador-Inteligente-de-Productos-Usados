package com.smartcompare.config.mercadolibre;

import com.smartcompare.config.mercadolibre.token.MercadoLibreTokenService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MercadoLibreApiTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final MercadoLibreTokenService tokenService;

    public MercadoLibreApiTest(MercadoLibreTokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String buscarProductos(String query) {
        String url = "https://api.mercadolibre.com/sites/MPE/search?q=" + query;
        String accessToken = tokenService.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}