package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // Rotas públicas
                .requestMatchers(
                    "/api/register",
                    "/api/login"
                ).permitAll()


                // Rotas somente ADMIN
                .requestMatchers(
                    "/api/admin/**",
                    "/api/usuarios/**"
                ).hasRole("ADMIN")


                // Rotas CLIENTE e ADMIN
                .requestMatchers(
                    "/api/guias/**",
                    "/api/pagamentos/**"
                ).hasAnyRole("ADMIN", "CLIENTE")


                // qualquer outra rota precisa estar autenticada
                .anyRequest().authenticated()
            );

        return http.build();
    }
}