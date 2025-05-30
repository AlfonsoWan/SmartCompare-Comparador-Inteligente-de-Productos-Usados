package com.smartcompare.product.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class EbaySearchResponse {
    @JsonProperty("itemSummaries")
    private List<EbayProductDTO> items;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("offset")
    private Integer offset;
}

