package com.smartcompare.product.infrastructure;

import lombok.RequiredArgsConstructor;
import com.smartcompare.product.application.ProductService;
import com.smartcompare.product.domain.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * Obtiene todos los productos paginados y ordenados, con filtros opcionales.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String source
    ) {
        // Filtros básicos (puedes mejorar con Specification o QueryDSL)
        List<ProductDTO> productos = productService.findAllPaged(page, size, sortBy);
        if (name != null) {
            productos = productos.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        if (source != null) {
            productos = productos.stream().filter(p -> p.getSource().equalsIgnoreCase(source)).toList();
        }
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Crea un nuevo producto.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> create(@Validated @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.save(dto));
    }

    /**
     * Busca productos en MercadoLibre.
     */
    @GetMapping("/mercadolibre/search")
    public ResponseEntity<?> searchInMercadoLibre(
            @RequestParam String query,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(productService.searchInMercadoLibre(query, offset, limit));
    }
}
