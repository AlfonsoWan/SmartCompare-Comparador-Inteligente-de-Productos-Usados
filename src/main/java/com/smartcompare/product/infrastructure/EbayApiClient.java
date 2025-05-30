package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.dto.EbaySearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EbayApiClient {
    @Value("${ebay.api.base-url:https://api.ebay.com}")
    private String baseUrl;

    @Value("${ebay.api.browse-endpoint:/buy/browse/v1/item_summary/search}")
    private String browseEndpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    public EbaySearchResponse searchProducts(String query, int limit, String accessToken) {
        String url = baseUrl + browseEndpoint + "?q=" + query + "&limit=" + limit;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "application/json");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<EbaySearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                EbaySearchResponse.class
        );
        return response.getBody();
    }
}

