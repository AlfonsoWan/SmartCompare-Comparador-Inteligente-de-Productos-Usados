package com.smartcompare.config.mercadolibre.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MercadoLibreTokenRepository extends JpaRepository<MercadoLibreToken, Long> {
    MercadoLibreToken findTopByOrderByIdDesc();
}