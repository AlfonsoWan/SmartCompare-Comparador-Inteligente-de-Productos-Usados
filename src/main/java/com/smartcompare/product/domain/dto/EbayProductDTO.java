package com.smartcompare.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EbayProductDTO {
    private String itemId;
    private String title;
    @JsonProperty("itemWebUrl")
    private String itemWebUrl;
    private String condition;

    // Mapeo de precio anidado
    @JsonProperty("price")
    private void unpackPrice(Object priceObj) {
        if (priceObj instanceof java.util.Map priceMap) {
            Object value = priceMap.get("value");
            Object currency = priceMap.get("currency");
            if (value != null) this.price = new BigDecimal(value.toString());
            if (currency != null) this.currency = currency.toString();
        }
    }
    private BigDecimal price;
    private String currency;

    // Mapeo de imagen anidada
    @JsonProperty("image")
    private void unpackImage(Object imageObj) {
        if (imageObj instanceof java.util.Map imageMap) {
            Object imageUrl = imageMap.get("imageUrl");
            if (imageUrl != null) this.imageUrl = imageUrl.toString();
        }
    }
    private String imageUrl;

    // Mapeo de ubicación anidada
    @JsonProperty("itemLocation")
    private void unpackLocation(Object locationObj) {
        if (locationObj instanceof java.util.Map locMap) {
            Object country = locMap.get("country");
            Object postalCode = locMap.get("postalCode");
            StringBuilder sb = new StringBuilder();
            if (country != null) sb.append(country);
            if (postalCode != null) sb.append(" (").append(postalCode).append(")");
            this.location = sb.toString();
        }
    }
    private String location;
}

