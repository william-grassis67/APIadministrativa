package com.example.demo.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http

            // Desabilita CSRF para API REST
            .csrf(csrf -> csrf.disable())


            // Configuração CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))


            // Permissões
            .authorizeHttpRequests(auth -> auth


                // Libera requisições OPTIONS (CORS Preflight)
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()


                // Rotas públicas
                .requestMatchers("/api/login")
                .permitAll()


                .requestMatchers("/api/register")
                .permitAll()


                // Temporariamente libera todas as rotas
                // depois pode trocar por authenticated()
                .anyRequest()
                .permitAll()
            );


        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration config = new CorsConfiguration();


        /*
         * Permite frontend local
         * e produção na Vercel
         */
        config.setAllowedOriginPatterns(List.of(

                "https://sapimanageradministration.vercel.app",
                "https://*.vercel.app",

                "http://localhost:*",
                "http://127.0.0.1:*"
        ));



        /*
         * Métodos permitidos
         */
        config.setAllowedMethods(List.of(

                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));



        /*
         * Aceita qualquer header enviado pelo frontend
         */
        config.setAllowedHeaders(List.of("*"));



        /*
         * Headers que o navegador pode ler
         */
        config.setExposedHeaders(List.of(
                "Authorization"
        ));



        /*
         * Permite cookies e Authorization
         */
        config.setAllowCredentials(true);



        /*
         * Cache do preflight
         */
        config.setMaxAge(3600L);



        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                config
        );


        return source;
    }

}