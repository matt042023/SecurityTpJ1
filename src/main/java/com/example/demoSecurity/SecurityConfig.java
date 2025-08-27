package com.example.demoSecurity.config;

import com.example.demoSecurity.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Encodeur de mot de passe BCrypt (sécurisé)
     * IMPORTANT : Ce bean doit être créé AVANT CustomUserDetailsService
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de la chaîne de filtres de sécurité
     * Spring va automatiquement utiliser le CustomUserDetailsService disponible
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Spring Security trouvera automatiquement le CustomUserDetailsService par type
                .authorizeHttpRequests(auth -> auth
                        // URLs publiques (accessibles sans authentification)
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/article/list").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/user-app/register").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")                    // Page de login personnalisée
                        .defaultSuccessUrl("/article/list", true)  // Redirection après login réussi
                        .failureUrl("/login?error=true")       // Redirection en cas d'erreur
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true") // Redirection après logout
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable) // Désactivé pour H2 Console et formulaires
                .headers(headers -> headers
                        .frameOptions().disable() // Permet l'affichage de H2 Console
                );

        return http.build();
    }
}