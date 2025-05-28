package com.smartcompare.comparison.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comparisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comparison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "comparison_products", joinColumns = @JoinColumn(name = "comparison_id"))
    @Column(name = "product_id")
    private List<Long> productIds;

    private LocalDateTime date;

    private Long userId;
}
