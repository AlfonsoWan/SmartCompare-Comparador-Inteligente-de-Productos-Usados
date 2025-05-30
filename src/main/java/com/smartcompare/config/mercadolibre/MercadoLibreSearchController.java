package com.smartcompare.config.mercadolibre;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcompare.product.domain.Product;
import com.smartcompare.product.infrastructure.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MercadoLibreSearchController {
    @Autowired
    private MercadoLibreAuthService authService;
    @Autowired
    private ProductRepository productRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/mercadolibre/search")
    public ResponseEntity<?> buscarProductos(@RequestParam(name = "q") String query) {
        String accessToken = authService.getAccessToken();
        String url = "https://api.mercadolibre.com/sites/MLA/search?q=" + query;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode results = root.get("results");
            if (results != null && results.isArray()) {
                for (JsonNode item : results) {
                    Product product = Product.builder()
                        .name(item.path("title").asText())
                        .price(item.path("price").asDouble())
                        .image(item.path("thumbnail").asText())
                        .source("MERCADOLIBRE")
                        .url(item.path("permalink").asText())
                        .build();
                    productRepository.save(product);
                }
            }
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar productos: " + e.getMessage());
        }
    }
}
