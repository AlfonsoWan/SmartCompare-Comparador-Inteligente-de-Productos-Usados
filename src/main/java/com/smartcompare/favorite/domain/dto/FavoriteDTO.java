package com.smartcompare.favorite.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDTO {
    private Long id;

    @NotBlank
    private Long productId;

    @NotBlank
    private Long userId;

    @NotBlank
    private LocalDateTime savedDate;
}