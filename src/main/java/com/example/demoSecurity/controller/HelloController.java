package com.example.demoSecurity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class HelloController {

    @GetMapping("/hello-public")
    public String helloPublic() {
        return "hello public - Accessible à tous !";
    }

    @GetMapping("/hello-private")
    public String helloPrivate(Authentication authentication) {
        // Récupération des informations de l'utilisateur connecté
        String username = authentication.getName();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return String.format(
                "🔒 hello private - Utilisateur connecté: %s | Rôles: %s | Type d'auth: %s",
                username,
                authorities,
                authentication.getClass().getSimpleName()
        );
    }

    @PostMapping("/hello-public")
    public String helloPublicPost() {
        return "hello public via POST - Méthode POST autorisée";
    }

    // Nouvel endpoint pour explorer l'objet Authentication
    @GetMapping("/user-info")
    public String getUserInfo(Authentication authentication) {
        if (authentication == null) {
            return "❌ Aucun utilisateur connecté";
        }

        StringBuilder info = new StringBuilder();
        info.append("👤 INFORMATIONS UTILISATEUR\n\n");
        info.append("Nom d'utilisateur: ").append(authentication.getName()).append("\n");
        info.append("Est authentifié: ").append(authentication.isAuthenticated()).append("\n");
        info.append("Principal: ").append(authentication.getPrincipal().getClass().getSimpleName()).append("\n");
        info.append("Credentials: ").append(authentication.getCredentials() != null ? "***" : "null").append("\n");
        info.append("Autorités/Rôles: ");

        authentication.getAuthorities().forEach(authority ->
                info.append(authority.getAuthority()).append(" ")
        );

        info.append("\n\nDétails: ").append(authentication.getDetails());

        return info.toString();
    }

    // Endpoint pour tester différents niveaux d'autorisation
    @GetMapping("/admin-only")
    public String adminOnly(Authentication authentication) {
        return "🔑 Zone ADMIN - Utilisateur: " + authentication.getName();
    }
}