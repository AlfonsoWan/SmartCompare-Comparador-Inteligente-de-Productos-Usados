package com.smartcompare.product.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private Double price;


    private String image;

    @NotBlank
    @Column(nullable = false)
    private String source; // Ej: MERCADOLIBRE, OLX

    @NotBlank
    @Column(nullable = false)
    private String url;
}