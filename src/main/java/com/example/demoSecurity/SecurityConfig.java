package com.example.demoSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Autorise l'accès à H2 Console sans authentification (mode développement)
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/user-app/**").permitAll()
                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) // Désactive CSRF pour H2 Console
                .headers(headers -> headers
                        .frameOptions().disable() // Permet l'affichage de H2 Console dans une iframe
                )
                .httpBasic(); // Authentification basique pour le reste

        return http.build();
    }


}
