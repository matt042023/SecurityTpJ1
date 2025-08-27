package com.example.demoSecurity.controller;

import com.example.demoSecurity.entity.Article;
import com.example.demoSecurity.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Créé un nouvel article via formulaire
     * POST /article/new
     */
    @PostMapping("/new")
    public String registerArticle(@ModelAttribute Article article, Authentication authentication) throws Exception {

        // Récupération de l'utilisateur connecté
        if (authentication != null && authentication.isAuthenticated()) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            article.setAuteur(username);
        }

        articleRepository.save(new Article(article.getTitre(), article.getContenu(), article.getAuteur()));
        return "redirect:/article/list";
    }

    /**
     * Affiche la liste de tous les articles
     * GET /article/list
     */
    @GetMapping("/list")
    public String listArticles(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "article-list";
    }

    /**
     * API REST - Récupère tous les articles
     * GET /article
     */
    @GetMapping
    @ResponseBody
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * API REST - Créé un article
     * POST /article
     */
    @PostMapping
    @ResponseBody
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }
}