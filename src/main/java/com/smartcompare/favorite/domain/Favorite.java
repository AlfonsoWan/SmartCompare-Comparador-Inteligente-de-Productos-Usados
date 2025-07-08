package com.smartcompare.favorite.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private Long userId;
    private LocalDateTime savedDate;
    @Column(length = 500) // Aumentar para títulos largos
    private String title;
    @Column(length = 500) // URLs pueden ser largas
    private String image;
    private Double price;
    private String currency;
    private String condition;
    @Column(length = 500) // URLs largas
    private String url;
}
