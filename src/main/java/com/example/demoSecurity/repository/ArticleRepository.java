package com.example.demoSecurity.repository;

import com.example.demoSecurity.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    /**
     * Recherche un article par son titre
     */
    Optional<Article> findByTitre(String titre);

    /**
     * Recherche tous les articles d'un auteur
     */
    List<Article> findByAuteur(String auteur);

    /**
     * Vérifie si un article existe avec ce titre
     */
    boolean existsByTitre(String titre);
}