package com.example.demoSecurity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_app")
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Constructeur utilitaire pour créer un utilisateur avec email et password
    public UserApp(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Override toString pour éviter l'affichage du mot de passe dans les logs
    @Override
    public String toString() {
        return "UserApp{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}