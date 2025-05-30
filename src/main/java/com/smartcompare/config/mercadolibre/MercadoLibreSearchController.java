package com.smartcompare.config.mercadolibre;

import com.smartcompare.config.mercadolibre.token.MercadoLibreTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@RestController
public class MercadoLibreSearchController {

    @Autowired
    private MercadoLibreTokenService tokenService;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/mercadolibre/search")
    public ResponseEntity<String> buscarProductos(@RequestParam(name = "q") String query) {
        String accessToken = tokenService.getValidAccessToken();
        String url = "https://api.mercadolibre.com/sites/MPE/search?q=" + query;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}