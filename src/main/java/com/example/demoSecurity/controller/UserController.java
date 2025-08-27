package com.example.demoSecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    /**
     * Endpoint REST pour récupérer les infos utilisateur en JSON
     * Utilise la classe Authentication pour extraire les données
     */
    @GetMapping("/user-info")
    @ResponseBody
    public Map<String, Object> getUserInfo(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            // Récupération du nom d'utilisateur
            String username = authentication.getName();

            // Récupération des rôles/autorités
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            String roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(", "));

            // Récupération du principal (peut être UserDetails ou String)
            Object principal = authentication.getPrincipal();

            // Construction de la réponse
            userInfo.put("username", username);
            userInfo.put("roles", roles);
            userInfo.put("isAuthenticated", authentication.isAuthenticated());
            userInfo.put("authenticationClass", authentication.getClass().getSimpleName());
            userInfo.put("principalClass", principal.getClass().getSimpleName());
            userInfo.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            // Si le principal est UserDetails, on peut récupérer plus d'infos
            if (principal instanceof UserDetails userDetails) {
                userInfo.put("accountNonExpired", userDetails.isAccountNonExpired());
                userInfo.put("accountNonLocked", userDetails.isAccountNonLocked());
                userInfo.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());
                userInfo.put("enabled", userDetails.isEnabled());
            }
        } else {
            userInfo.put("message", "Utilisateur non authentifié");
            userInfo.put("isAuthenticated", false);
        }

        return userInfo;
    }

    /**
     * Page de profil utilisateur avec vue Thymeleaf
     * Utilise Authentication pour afficher les infos dans une page HTML
     */
    @GetMapping("/profile")
    public String getUserProfile(Authentication authentication, Model model) {

        if (authentication != null && authentication.isAuthenticated()) {
            // Extraction des informations utilisateur
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            // Ajout des données au modèle pour Thymeleaf
            model.addAttribute("username", username);
            model.addAttribute("authorities", authorities);
            model.addAttribute("isAuthenticated", authentication.isAuthenticated());
            model.addAttribute("authenticationClass", authentication.getClass().getSimpleName());
            model.addAttribute("connectionTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

            // Informations détaillées si UserDetails
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("userDetails", userDetails);
                model.addAttribute("hasUserDetails", true);
            } else {
                model.addAttribute("hasUserDetails", false);
            }

            // Vérification des rôles spécifiques
            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            boolean isDeveloper = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_DEVELOPER"));
            boolean isManager = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_MANAGER"));

            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isDeveloper", isDeveloper);
            model.addAttribute("isManager", isManager);
        }

        return "profile"; // Retourne la vue profile.html
    }

    /**
     * Endpoint pour tester les différents niveaux d'autorisation
     */
    @GetMapping("/admin/dashboard")
    @ResponseBody
    public String adminDashboard(Authentication authentication) {
        return String.format("🔐 Dashboard Admin - Connecté en tant que: %s avec les rôles: %s",
                authentication.getName(),
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "))
        );
    }
}