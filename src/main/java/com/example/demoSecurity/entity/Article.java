package com.example.demoSecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @Column(nullable = true)
    private String auteur;

    /**
     * Constructeur pour créer un article avec titre et contenu
     */
    public Article(String titre, String contenu) {
        this.titre = titre;
        this.contenu = contenu;
    }

    /**
     * Constructeur pour créer un article avec titre, contenu et auteur
     */
    public Article(String titre, String contenu, String auteur) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
    }
}