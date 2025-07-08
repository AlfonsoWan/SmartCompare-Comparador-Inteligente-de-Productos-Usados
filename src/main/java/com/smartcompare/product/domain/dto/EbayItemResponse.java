package com.smartcompare.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EbayItemResponse {
    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Price price;

    @JsonProperty("image")
    private Image image;

    @JsonProperty("itemWebUrl")
    private String itemWebUrl;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Price {
        @JsonProperty("value")
        private Double value;
        @JsonProperty("currency")
        private String currency;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        @JsonProperty("imageUrl")
        private String imageUrl;
    }
}
