package com.smartcompare.config.mercadolibre;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MercadoLibreTokenConfig {
    @Id
    private String key = "mercadolibre";
    private String refreshToken;
}

