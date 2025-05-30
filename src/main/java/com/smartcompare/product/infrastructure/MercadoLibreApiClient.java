package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.dto.MercadoLibreSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MercadoLibreApiClient {
    @Value("${mercadolibre.site-id:MPE}")
    private String siteId;

    private final RestTemplate restTemplate = new RestTemplate();

    public MercadoLibreSearchResponse searchProducts(String query, Integer offset, Integer limit) {
        StringBuilder url = new StringBuilder("https://api.mercadolibre.com/sites/" + siteId + "/search?q=" + query);
        if (offset != null) {
            url.append("&offset=").append(offset);
        }
        if (limit != null) {
            url.append("&limit=").append(limit);
        }
        ResponseEntity<MercadoLibreSearchResponse> response = restTemplate.getForEntity(url.toString(), MercadoLibreSearchResponse.class);
        return response.getBody();
    }
}

