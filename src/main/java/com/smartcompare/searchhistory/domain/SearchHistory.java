package com.smartcompare.searchhistory.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String terms;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
