package com.smartcompare.product.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private Double price;

    @NotBlank
    private String image;

    @NotBlank
    private String source;

    @NotBlank
    private String url;
}