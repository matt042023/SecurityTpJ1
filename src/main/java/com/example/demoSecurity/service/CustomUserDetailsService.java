package com.example.demoSecurity.service;

import com.example.demoSecurity.entity.UserApp;
import com.example.demoSecurity.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAppRepository repo;
    private final PasswordEncoder bcrypt;

    // Injection par constructeur pour éviter les références circulaires
    public CustomUserDetailsService(UserAppRepository repo, PasswordEncoder bcrypt) {
        this.repo = repo;
        this.bcrypt = bcrypt;
    }

    /**
     * Méthode pour créer un utilisateur avec mot de passe encodé
     */
    public void createUser(String username, String password) {
        repo.save(
                new UserApp(
                        username,
                        bcrypt.encode(password) // Encode le mot de passe avec BCrypt
                )
        );
    }

    /**
     * Méthode requise par UserDetailsService
     * Charge un utilisateur par son nom d'utilisateur (email) pour Spring Security
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}