package com.smartcompare.searchhistory.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryDTO {
    private Long id;

    @NotBlank
    private String terms;

    @NotBlank
    private LocalDateTime date;

    @NotBlank
    private Long userId;
}