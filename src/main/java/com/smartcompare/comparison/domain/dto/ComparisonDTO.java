package com.smartcompare.comparison.domain.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComparisonDTO {
    private Long id;
    private List<Long> productIds;
    private LocalDateTime date;
    private Long userId;
}