package com.smartcompare.favorite.domain.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private LocalDateTime savedDate;

    // Datos mínimos del producto eBay para guardar favoritos directamente
    private String ebayItemId;
    private String title;
    private Double price;
    private String image;
    private String url;
    private String condition;
    private String currency;
}
