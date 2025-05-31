package com.smartcompare.product.application;

import com.smartcompare.product.domain.Product;
import com.smartcompare.product.domain.dto.ProductDTO;
import com.smartcompare.product.domain.exception.ProductNotFoundException;
import com.smartcompare.product.infrastructure.EbayApiClient;
import com.smartcompare.product.infrastructure.ProductRepository;
import com.smartcompare.product.infrastructure.EbayOAuthService;
import com.smartcompare.smartranking.application.SmartRankingService;
import com.smartcompare.searchhistory.application.SearchHistoryService;
import com.smartcompare.recommendation.application.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private EbayApiClient ebayApiClient;
    @Mock
    private EbayOAuthService ebayOAuthService;
    @Mock
    private SmartRankingService smartRankingService;
    @Mock
    private SearchHistoryService searchHistoryService;
    @Mock
    private RecommendationService recommendationService;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(
            productRepository,
            ebayApiClient,
            ebayOAuthService,
            smartRankingService,
            searchHistoryService,
            recommendationService
        );
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

