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
        http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(req -> req.requestMatchers("/hello-public").permitAll())
                .authorizeHttpRequests(req -> req.requestMatchers("/hello-private").authenticated())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
