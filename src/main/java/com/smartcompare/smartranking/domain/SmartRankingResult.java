package com.smartcompare.smartranking.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "smart_ranking_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartRankingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchTerms;
    private LocalDateTime date;
    private Long userId;

    @ElementCollection
    @CollectionTable(name = "smart_ranking_top_products", joinColumns = @JoinColumn(name = "ranking_id"))
    @Column(name = "ebay_item_id")
    private List<String> topProductItemIds;

    @Column(length = 1000)
    private String justification;
}

