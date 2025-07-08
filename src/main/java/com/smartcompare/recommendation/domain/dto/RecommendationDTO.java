package com.smartcompare.recommendation.domain.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationDTO {
    private Long id;
    private String suggestedProductId;
    private String reason;
    private Long userId;
}
