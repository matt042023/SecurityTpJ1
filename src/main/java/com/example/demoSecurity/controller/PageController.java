package com.example.demoSecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    /**
     * Affiche la page d'inscription
     */
    @GetMapping("/register")
    public String createUserPage() {
        return "register";
    }

    /**
     * Affiche la page de connexion
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Affiche la page pour créer un article
     */
    @GetMapping("/add-article")
    public String addArticle() {
        return "add-article";
    }
}