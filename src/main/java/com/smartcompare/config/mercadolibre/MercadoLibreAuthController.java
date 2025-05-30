package com.smartcompare.config.mercadolibre;

import com.smartcompare.config.mercadolibre.token.MercadoLibreTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MercadoLibreAuthController {

    private final MercadoLibreAuthService authService;
    private final MercadoLibreTokenService tokenService;

    public MercadoLibreAuthController(MercadoLibreAuthService authService, MercadoLibreTokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @GetMapping("/auth/mercadolibre")
    public String iniciarAutenticacion(@RequestParam String codeChallenge) {
        return authService.generarUrlAutorizacion(codeChallenge);
    }

    @GetMapping("/auth/mercadolibre/callback")
    public String callback(@RequestParam String code, @RequestParam (required = false) String codeVerifier) {
        String tokenResponse = authService.obtenerToken(code, codeVerifier);
        System.out.println("Token recibido: " + tokenResponse);
        tokenService.saveToken(tokenResponse);
        return "Token guardado correctamente";
    }
}