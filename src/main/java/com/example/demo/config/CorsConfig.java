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


        /*
         * Origens permitidas
         * Aceita Vercel produção e previews
         */
        config.setAllowedOriginPatterns(List.of(

                "https://sapimanageradministration.vercel.app",
                "https://*.vercel.app",

                "http://localhost:*",
                "http://127.0.0.1:*"
        ));



        /*
         * Métodos HTTP liberados
         */
        config.setAllowedMethods(List.of(

                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));



        /*
         * Aceita todos os headers enviados pelo frontend
         */
        config.setAllowedHeaders(List.of("*"));



        /*
         * Headers que o navegador pode acessar
         */
        config.setExposedHeaders(List.of(
                "Authorization"
        ));



        /*
         * Permite cookies e autenticação
         */
        config.setAllowCredentials(true);



        /*
         * Tempo de cache do preflight
         */
        config.setMaxAge(3600L);



        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                config
        );


        return new CorsFilter(source);
    }
}