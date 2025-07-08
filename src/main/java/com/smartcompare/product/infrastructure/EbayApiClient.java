package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.dto.EbayItemResponse;
import com.smartcompare.product.domain.dto.EbaySearchResponse;
import com.smartcompare.product.domain.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class EbayApiClient {
    @Value("${ebay.api.base-url:https://api.ebay.com}")
    private String baseUrl;

    @Value("${ebay.api.browse-endpoint:/buy/browse/v1/item_summary/search}")
    private String browseEndpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    public EbaySearchResponse searchProducts(String query, int limit, String accessToken, String zipCode, Double radius) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl + browseEndpoint)
                .queryParam("q", query)
                .queryParam("limit", limit);

        // Si hay filtros geográficos, los agregamos como múltiples parámetros 'filter' según la documentación oficial
        if (zipCode != null && !zipCode.isEmpty() && radius != null) {
            // Filtros obligatorios para local pickup geo-localizado
            uriBuilder.queryParam("filter", "deliveryOptions:{SELLER_ARRANGED_LOCAL_PICKUP}");
            uriBuilder.queryParam("filter", "pickupCountry:US");
            uriBuilder.queryParam("filter", "pickupPostalCode:" + zipCode);
            uriBuilder.queryParam("filter", "pickupRadius:" + radius.intValue());
            uriBuilder.queryParam("filter", "pickupRadiusUnit:mi");
            uriBuilder.queryParam("sort", "distance");
        }

        String url = uriBuilder.toUriString();
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

    // Sobrecarga para compatibilidad retroactiva
    public EbaySearchResponse searchProducts(String query, int limit, String accessToken) {
        return searchProducts(query, limit, accessToken, null, null);
    }

    public ProductDTO getProductById(String ebayItemId, String accessToken) {
        // Endpoint de eBay para obtener detalles de un ítem individual
        String url = "https://api.ebay.com/buy/browse/v1/item/" + ebayItemId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/json");
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<EbayItemResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    EbayItemResponse.class
            );
            EbayItemResponse ebayItem = response.getBody();
            if (ebayItem == null) {
                return null;
            }

            Double price = null;
            if (ebayItem.getPrice() != null) {
                try {
                    price = ebayItem.getPrice().getValue();
                } catch (NumberFormatException e) {
                    // Log error pero continúa con precio null
                    log.error("Error parsing price for item {}: {}", ebayItem.getItemId(), e.getMessage());
                }
            }

            return ProductDTO.builder()
                    .id(ebayItem.getItemId()) // Ya es String, no necesita conversión
                    .name(ebayItem.getTitle() != null ? ebayItem.getTitle().trim() : null)
                    .price(price)
                    .image(ebayItem.getImage() != null ? ebayItem.getImage().getImageUrl() : null)
                    .source("EBAY")
                    .url(ebayItem.getItemWebUrl())
                    .build();
        } catch (Exception e) {
            log.error("Error getting item from eBay: {}", e.getMessage());
            return null;
        }
    }

    public EbaySearchResponse searchProductsByCategory(String categoryId, int limit, String accessToken) {
        String url = baseUrl + "/buy/browse/v1/item_summary/search?category_ids="
                + categoryId + "&limit=" + limit;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<EbaySearchResponse> resp = restTemplate
                .exchange(url, HttpMethod.GET, entity, EbaySearchResponse.class);
        return resp.getBody();
    }
}
