package com.example.demoSecurity.controller;

import com.example.demoSecurity.entity.UserApp;
import com.example.demoSecurity.repository.UserAppRepository;
import com.example.demoSecurity.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user-app")
public class UserAppController {

    private final UserAppRepository userAppRepository;
    private final CustomUserDetailsService customUserDetailsService;

    // Injection par constructeur (recommandée)
    public UserAppController(UserAppRepository userAppRepository,
                             CustomUserDetailsService customUserDetailsService) {
        this.userAppRepository = userAppRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Récupère tous les utilisateurs
     */
    @GetMapping
    @ResponseBody
    public List<UserApp> userApp() throws Exception {
        return userAppRepository.findAll();
    }

    /**
     * Endpoint d'inscription utilisateur via formulaire
     * POST /user-app/register
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserApp userApp) throws Exception {
        customUserDetailsService.createUser(
                userApp.getEmail(),
                userApp.getPassword()
        );
        return "redirect:/login";
    }

    /**
     * Récupère un utilisateur par son ID
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserApp> getUserById(@PathVariable Integer id) {
        Optional<UserApp> user = userAppRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Créé un nouvel utilisateur (API REST)
     */
    @PostMapping
    @ResponseBody
    public UserApp createUser(@RequestBody UserApp userApp) {
        return userAppRepository.save(userApp);
    }

    /**
     * Recherche un utilisateur par email
     */
    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<UserApp> findByEmail(@RequestParam String email) {
        Optional<UserApp> user = userAppRepository.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Compte le nombre d'utilisateurs
     */
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userAppRepository.count());
    }
}