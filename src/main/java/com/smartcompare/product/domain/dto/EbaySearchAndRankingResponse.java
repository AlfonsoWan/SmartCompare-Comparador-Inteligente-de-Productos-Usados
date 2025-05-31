package com.smartcompare.product.domain.dto;

import com.smartcompare.smartranking.domain.dto.SmartRankingResultDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EbaySearchAndRankingResponse {
    private EbaySearchResponse ebaySearchResponse;
    private SmartRankingResultDTO rankingResult;
}

