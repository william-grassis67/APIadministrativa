package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // Domínios permitidos
        config.setAllowedOrigins(List.of(
                "http://localhost:5500",
                "http://127.0.0.1:5500",
                "http://localhost:8080",
                "https://sapimanageradministration.vercel.app",
                "https://sapimanageradministration.vercel.app/index.html"
        ));

        // Métodos permitidos
        config.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        // Cabeçalhos permitidos
        config.setAllowedHeaders(List.of("*"));

        // Cabeçalhos expostos
        config.setExposedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        // Permitir cookies/token
        config.setAllowCredentials(true);

        // Cache do preflight
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}