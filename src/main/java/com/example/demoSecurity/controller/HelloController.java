package com.example.demoSecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class HelloController {

    @GetMapping("/hello-public")
    public String helloPublic() {
        return "🌐 Hello public - Accessible à tous !";
    }

    @GetMapping("/hello-private")
    public String helloPrivate(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            String roles = authentication.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.joining(", "));

            return String.format("🔒 Hello privé ! Connecté en tant que: %s avec les rôles: %s",
                    username, roles);
        }
        return "🔒 Hello privé - Authentification requise";
    }

    @PostMapping("/hello-public")
    public String helloPublicPost(Authentication authentication) {
        String authInfo = (authentication != null && authentication.isAuthenticated())
                ? " (connecté: " + authentication.getName() + ")"
                : " (anonyme)";
        return "🌐 Hello public via POST" + authInfo;
    }
}