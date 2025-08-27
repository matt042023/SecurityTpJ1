package com.example.demoSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configuration des utilisateurs en mémoire
     * Alternative à application.properties pour plus de flexibilité
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        // Utilisateur 1 : Développeur principal
        UserDetails matthieu = User.builder()
                .username("matthieu")
                .password(passwordEncoder.encode("dev2024"))
                .roles("USER", "ADMIN", "DEVELOPER")
                .build();

        // Utilisateur 2 : Utilisateur standard
        UserDetails alice = User.builder()
                .username("alice")
                .password(passwordEncoder.encode("alice123"))
                .roles("USER")
                .build();

        // Utilisateur 3 : Manager
        UserDetails bob = User.builder()
                .username("bob")
                .password(passwordEncoder.encode("manager456"))
                .roles("USER", "MANAGER")
                .build();

        // Utilisateur 4 : Admin système
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin789"))
                .roles("ADMIN", "SYSTEM")
                .build();

        return new InMemoryUserDetailsManager(matthieu, alice, bob, admin);
    }

    /**
     * Encodeur de mot de passe BCrypt (sécurisé)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration de la chaîne de filtres de sécurité
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // Pages publiques
                        .requestMatchers("/hello-public", "/login", "/user-info", "/css/**", "/js/**").permitAll()
                        // Pages nécessitant une authentification
                        .requestMatchers("/hello-private", "/profile", "/admin/**").authenticated()
                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile", true)  // Redirection vers le profil après login
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}