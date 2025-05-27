package com.smartcompare.searchhistory.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryDTO {
    private Long id;
    private String terms;
    private LocalDateTime date;
    private Long userId;
}
