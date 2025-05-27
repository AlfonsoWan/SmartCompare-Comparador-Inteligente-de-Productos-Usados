package com.smartcompare.product.application;

import com.smartcompare.product.domain.Product;
import com.smartcompare.product.domain.dto.ProductDTO;
import com.smartcompare.product.domain.exception.ProductNotFoundException;
import com.smartcompare.product.infrastructure.ProductRepository;
import com.smartcompare.product.infrastructure.MercadoLibreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private MercadoLibreClient mercadoLibreClient;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, mercadoLibreClient);
    }

    @Test
    void testFindById_found() {
        Product product = Product.builder().id(1L).name("Test").price(100.0).build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDTO dto = productService.findById(1L);
        assertEquals("Test", dto.getName());
        assertEquals(100.0, dto.getPrice());
    }

    @Test
    void testFindById_notFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findById(2L));
    }
}

