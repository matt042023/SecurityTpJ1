package com.example.demoSecurity.controller;

import com.example.demoSecurity.entity.UserApp;
import com.example.demoSecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-app")
public class UserAppController {

    @Autowired
    private UserAppRepository userAppRepository;

    /**
     * Récupère tous les utilisateurs
     * GET /user-app
     */
    @GetMapping
    public List<UserApp> userApp() throws Exception {
        return userAppRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son ID
     * GET /user-app/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable Integer id) {
        Optional<UserApp> user = userAppRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Créé un nouvel utilisateur
     * POST /user-app
     * Body: {"email": "test@test.com", "password": "password123"}
     */
    @PostMapping
    public UserApp createUser(@RequestBody UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    /**
     * Recherche un utilisateur par email
     * GET /user-app/search?email=test@test.com
     */
    @GetMapping("/search")
    public ResponseEntity<UserApp> findByEmail(@RequestParam String email) {
        Optional<UserApp> user = userAppRepository.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Supprime tous les utilisateurs (utile pour les tests)
     * DELETE /user-app/all
     */
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll() {
        userAppRepository.deleteAll();
        return ResponseEntity.ok("Tous les utilisateurs ont été supprimés");
    }

    /**
     * Compte le nombre d'utilisateurs
     * GET /user-app/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userAppRepository.count());
    }
}