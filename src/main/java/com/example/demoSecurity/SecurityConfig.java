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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Création de plusieurs utilisateurs en mémoire
        UserDetails matthieu = User.builder()
                .username("matthieu")
                .password(passwordEncoder.encode("password123"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails alice = User.builder()
                .username("alice")
                .password(passwordEncoder.encode("alice2025"))
                .roles("USER")
                .build();

        UserDetails bob = User.builder()
                .username("bob")
                .password(passwordEncoder.encode("bob2025"))
                .roles("USER", "MANAGER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN", "MANAGER", "USER")
                .build();

        // Utilisateur de test avec mot de passe simple (pour le développement)
        UserDetails testUser = User.builder()
                .username("test")
                .password(passwordEncoder.encode("test"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(matthieu, alice, bob, admin, testUser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // URLs publiques
                        .requestMatchers("/hello-public", "/login", "/css/**", "/js/**").permitAll()

                        // URLs nécessitant un rôle spécifique
                        .requestMatchers("/admin-only").hasRole("ADMIN")

                        // Toutes les autres URLs nécessitent une authentification
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/user-info", true)  // Redirige vers user-info après login
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