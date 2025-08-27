package com.example.demoSecurity.config;

import com.example.demoSecurity.entity.UserApp;
import com.example.demoSecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Classe pour initialiser des données de test au démarrage de l'application
 * Implémente CommandLineRunner pour s'exécuter après le démarrage Spring
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserAppRepository userAppRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifier si des données existent déjà
        if (userAppRepository.count() == 0) {
            System.out.println("=== Initialisation des données de test ===");

            // Créer des utilisateurs de test
            UserApp user1 = new UserApp("matthieu@dev.com", "password123");
            UserApp user2 = new UserApp("alice@test.com", "alice456");
            UserApp user3 = new UserApp("bob@manager.com", "bob789");
            UserApp user4 = new UserApp("admin@system.com", "admin999");

            // Sauvegarder en base
            userAppRepository.save(user1);
            userAppRepository.save(user2);
            userAppRepository.save(user3);
            userAppRepository.save(user4);

            System.out.println("Utilisateurs créés en base:");
            userAppRepository.findAll().forEach(System.out::println);
            System.out.println("=== Fin initialisation ===");
        } else {
            System.out.println("Données déjà présentes, aucune initialisation nécessaire");
        }
    }
}