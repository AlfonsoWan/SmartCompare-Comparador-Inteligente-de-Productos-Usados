package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.dto.MercadoLibreProductDTO;
import com.smartcompare.product.domain.dto.MercadoLibreSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mercadolibre", url = "https://api.mercadolibre.com")
public interface MercadoLibreClient {
    @GetMapping("/sites/MLA/search")
    MercadoLibreSearchResponse searchProducts(
            @RequestParam("q") String query,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestHeader("Accept") String acceptHeader
    );

    @GetMapping("/items/{id}")
    MercadoLibreProductDTO getProduct(@PathVariable("id") String id);
}
