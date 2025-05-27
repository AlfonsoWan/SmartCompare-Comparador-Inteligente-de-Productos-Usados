package com.smartcompare.product.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;

@FeignClient(name = "mercado-libre-client", url = "https://api.mercadolibre.com")
public interface MercadoLibreClient {
    @GetMapping("/sites/MLA/search")
    Map<String, Object> searchProducts(
        @RequestParam("q") String query,
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit,
        @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader
    );
}

