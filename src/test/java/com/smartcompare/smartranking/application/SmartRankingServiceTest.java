package com.smartcompare.smartranking.application;

import com.smartcompare.product.domain.dto.EbayProductDTO;
import com.smartcompare.smartranking.domain.SmartRankingResult;
import com.smartcompare.smartranking.domain.dto.SmartRankingResultDTO;
import com.smartcompare.smartranking.infrastructure.SmartRankingResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SmartRankingServiceTest {
    @Mock
    private SmartRankingResultRepository smartRankingResultRepository;
    @InjectMocks
    private SmartRankingService smartRankingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        smartRankingService = new SmartRankingService(smartRankingResultRepository);
    }

    @Test
    void testAnalyzeAndSave_rankingLogic() {
        EbayProductDTO p1 = new EbayProductDTO();
        p1.setItemId("1");
        p1.setTitle("Dell Latitude 7400 Laptop");
        p1.setCondition("Very Good - Refurbished");
        p1.setPrice(new BigDecimal("200.00"));
        p1.setCurrency("USD");
        EbayProductDTO p2 = new EbayProductDTO();
        p2.setItemId("2");
        p2.setTitle("Dell Latitude 7400 Laptop");
        p2.setCondition("Good - Refurbished");
        p2.setPrice(new BigDecimal("180.00"));
        p2.setCurrency("USD");
        List<EbayProductDTO> products = Arrays.asList(p1, p2);
        when(smartRankingResultRepository.save(any(SmartRankingResult.class))).thenAnswer(i -> i.getArgument(0));
        SmartRankingResultDTO result = smartRankingService.analyzeAndSave("Dell Latitude 7400", 1L, products);
        assertNotNull(result);
        assertEquals(2, result.getTopProductItemIds().size());
        assertTrue(result.getJustification().contains("Mejor opción"));
    }
}

