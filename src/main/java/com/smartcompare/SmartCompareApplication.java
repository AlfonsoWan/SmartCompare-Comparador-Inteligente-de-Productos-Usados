package com.smartcompare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.smartcompare.product.infrastructure")
public class SmartCompareApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartCompareApplication.class, args);
    }
}

