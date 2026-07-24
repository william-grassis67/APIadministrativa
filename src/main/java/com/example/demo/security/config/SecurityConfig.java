package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            // API REST não usa CSRF
            .csrf(csrf -> csrf.disable())


            // Ativa CORS configurado abaixo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))


            .authorizeHttpRequests(auth -> auth

                // Login
                .requestMatchers(
                        "/api/login"
                ).permitAll()


                // Cadastro
                .requestMatchers(
                        "/api/register"
                ).permitAll()


                // Pagamentos INSS
                .requestMatchers(
                        "/api/pagamento/**",
                        "/api/payments/**",
                        "/api/guias/**"
                ).permitAll()


                // Administração
                .requestMatchers(
                        "/api/admin/**",
                        "/api/usuarios/**",
                        "/api/clientes"
                ).permitAll()


                // Libera restante da API
                .anyRequest().permitAll()

            );


        return http.build();

    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration configuration = new CorsConfiguration();


        configuration.setAllowedOrigins(List.of(

                "http://localhost:5500",
                "http://127.0.0.1:5500"

        ));


        configuration.setAllowedMethods(List.of(

                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"

        ));


        configuration.setAllowedHeaders(List.of("*"));


        configuration.setAllowCredentials(true);



        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();


        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;

    }

}