package com.smartcompare.product.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String image;
    private String source;
    private String url;
}

