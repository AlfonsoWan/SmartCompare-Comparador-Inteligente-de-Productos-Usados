package com.smartcompare.product.infrastructure;

import com.smartcompare.product.application.ProductService;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.product.domain.dto.ProductDTO;
import com.smartcompare.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/ml")
    public ResponseEntity<List<ProductDTO>> searchInMercadoLibre(
            @RequestParam String query,
            @AuthenticationPrincipal User user  // tu User principal
    ) {
        // 1) Búsqueda en ML
        List<ProductDTO> results = productService.searchInMercadoLibre(query);
        // 2) Guardar en historial
        searchHistoryService.save(query, user.getId());
        return ResponseEntity.ok(results);
    }
}
