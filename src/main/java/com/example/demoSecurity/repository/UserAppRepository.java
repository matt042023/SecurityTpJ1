package com.example.demoSecurity.repository;

import com.example.demoSecurity.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Integer> {

    /**
     * Recherche un utilisateur par son email
     * @param email L'email à rechercher
     * @return Optional contenant l'utilisateur s'il existe
     */
    Optional<UserApp> findByEmail(String email);

    /**
     * Vérifie si un utilisateur existe avec cet email
     * @param email L'email à vérifier
     * @return true si l'utilisateur existe
     */
    boolean existsByEmail(String email);
}