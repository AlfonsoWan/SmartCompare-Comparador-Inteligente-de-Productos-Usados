package com.smartcompare.favorite.domain.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    private Long id;
    private Long productId;
    private Long userId;
    private LocalDateTime savedDate;
}

