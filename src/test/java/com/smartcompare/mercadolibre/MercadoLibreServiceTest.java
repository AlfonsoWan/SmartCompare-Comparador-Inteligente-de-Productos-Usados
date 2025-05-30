package com.smartcompare.mercadolibre;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.smartcompare.config.mercadolibre.token.MercadoLibreTokenRepository;
import com.smartcompare.config.mercadolibre.token.MercadoLibreTokenService;
import com.smartcompare.config.mercadolibre.token.MercadoLibreToken;

import static org.mockito.Mockito.*;

class MercadoLibreTokenServiceTest {

    @Mock
    private MercadoLibreTokenRepository repository;

    @InjectMocks
    private MercadoLibreTokenService tokenService;

    @Test
    void testSaveToken() throws Exception {
        MockitoAnnotations.openMocks(this);

        String tokenResponse = "{\"access_token\":\"abc123\",\"refresh_token\":\"xyz456\",\"expires_in\":3600,\"token_type\":\"Bearer\"}";

        tokenService.saveToken(tokenResponse);

        verify(repository, times(1)).save(any(MercadoLibreToken.class));
    }
}
