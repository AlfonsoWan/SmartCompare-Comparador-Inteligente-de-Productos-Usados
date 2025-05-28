package com.smartcompare.product.domain.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MercadoLibreProductDTO {
    private String id;
    private String title;
    private BigDecimal price;
    private String currency_id;
    private String thumbnail;
    private String permalink;
    private String condition;
    private Shipping shipping;
    private Location seller_address;

    @Data
    public static class Shipping {
        private Boolean free_shipping;
        private String mode;
    }

    @Data
    public static class Location {
        private String city;
        private String state;
    }
}
