package com.smartcompare.searchhistory.domain.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryDTO {
    private Long id;
    private Long userId;
    private String terms;
    private LocalDateTime date;
}