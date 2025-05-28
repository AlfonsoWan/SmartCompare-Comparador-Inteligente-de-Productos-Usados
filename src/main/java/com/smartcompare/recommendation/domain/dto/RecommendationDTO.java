package com.smartcompare.recommendation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationDTO {
    private Long id;

    @NotBlank
    private Long suggestedProductId;

    @NotBlank
    private String reason;

    @NotBlank
    private Long userId;
}
