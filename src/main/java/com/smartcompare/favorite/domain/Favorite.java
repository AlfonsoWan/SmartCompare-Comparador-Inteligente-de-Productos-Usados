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
    private String title;
    private String image;
    private Double price;
    private String currency;
    private String condition;
    private String url;
}
