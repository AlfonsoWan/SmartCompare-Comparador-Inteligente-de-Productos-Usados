package com.smartcompare.product.infrastructure;

import com.smartcompare.product.domain.exception.MercadoLibreNotFoundException;
import com.smartcompare.product.domain.exception.MercadoLibreServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        int status = response.status();
        if (status == 404 && methodKey.contains("getProduct")) {
            return new MercadoLibreNotFoundException(
                    "No se encontró el producto en MercadoLibre (HTTP 404): " + response.request().url()
            );
        }
        // Para cualquier otro error, devolvemos excepción genérica de servicio externo
        return new MercadoLibreServiceException(
                "Error en servicio externo MercadoLibre (HTTP " + status + "): " + response.request().url()
        );
    }
}
