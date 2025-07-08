package com.smartcompare.product.domain;

import jakarta.persistence.*;
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
    private String id;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 500)
    private String image;

    @Column(nullable = false)
    private String source; // Ej: MERCADOLIBRE, OLX

    @Column(nullable = false, length = 500)
    private String url;

    @Column(name = "primary_category_id", length = 50)
    private String primaryCategoryId;
}
