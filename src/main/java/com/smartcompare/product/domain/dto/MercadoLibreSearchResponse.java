package com.smartcompare.product.domain.dto;

import lombok.Data;
import java.util.List;

@Data
public class MercadoLibreSearchResponse {
    private List<MercadoLibreProductDTO> results;
    private Paging paging;

    @Data
    public static class Paging {
        private Integer total;
        private Integer offset;
        private Integer limit;
    }
}
