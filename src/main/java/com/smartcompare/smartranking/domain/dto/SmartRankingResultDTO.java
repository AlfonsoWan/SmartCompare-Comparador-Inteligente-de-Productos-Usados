package com.smartcompare.smartranking.domain.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartRankingResultDTO {
    private Long id;
    private String searchTerms;
    private LocalDateTime date;
    private Long userId;
    private List<String> topProductItemIds;
    private String justification;
}

