package com.smartcompare.searchhistory.domain.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistoryDTO {
    private Long id;
    private String terms;
    private LocalDateTime date;
    private Long userId;
}

