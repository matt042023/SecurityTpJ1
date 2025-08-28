# Spring Security - TP Jour 1

Projet d'apprentissage Spring Security développé dans le cadre de la formation développeur Java Spring Boot. Ce repository contient une progression complète des concepts de sécurité web, de la configuration basique à l'authentification avec base de données.

## Structure du Repository

Le projet est organisé en branches correspondant aux différents travaux pratiques :

- **`main`** : TP1a - Configuration Spring Security basique
- **`TP1b`** : TP1b - Formulaires de login personnalisés avec Thymeleaf
- **`TP1c`** : TP1c - Persistance en base de données avec JPA
- **`TP1d`** : TP1d - Système d'authentification complet avec utilisateurs BDD

## Concepts Mis en Œuvre

### TP1a - Fondamentaux Spring Security
- Configuration par défaut de Spring Security
- Endpoints publics vs privés
- Authentification HTTP Basic
- Tests avec Postman et navigateur
- Décodage des tokens d'authentification Base64

### TP1b - Interface Utilisateur Web
- Pages de login personnalisées avec Thymeleaf
- Configuration `SecurityFilterChain`
- Gestion des sessions utilisateur (cookies JSESSIONID)
- Redirection après authentification
- Gestion des échecs de connexion

### TP1c - Persistance des Données
- Entités JPA avec Lombok (`UserApp`, `Article`)
- Repositories Spring Data
- Base de données H2 en mémoire
- Console H2 pour administration
- Contrôleurs REST et vues Thymeleaf

### TP1d - Authentification Complète
- Service d'authentification personnalisé (`CustomUserDetailsService`)
- Encodage sécurisé des mots de passe (BCrypt)
- Inscription utilisateur via formulaires
- Gestion des auteurs d'articles
- Intégration complète Spring Security + JPA

## Technologies Utilisées

- **Java 21** - Langage principal
- **Spring Boot 3.5.5** - Framework principal
- **Spring Security 6.5.3** - Gestion de la sécurité
- **Spring Data JPA 3.5.3** - Persistance des données
- **Thymeleaf** - Moteur de templates
- **H2 Database** - Base de données en mémoire
- **Lombok** - Simplification du code
- **Maven** - Gestionnaire de dépendances

## Structure du Projet

```
src/
├── main/
│   ├── java/com/example/demoSecurity/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   └── DataInitializer.java
│   │   ├── controller/
│   │   │   ├── HelloController.java
│   │   │   ├── PageController.java
│   │   │   ├── UserAppController.java
│   │   │   └── ArticleController.java
│   │   ├── entity/
│   │   │   ├── UserApp.java
│   │   │   └── Article.java
│   │   ├── repository/
│   │   │   ├── UserAppRepository.java
│   │   │   └── ArticleRepository.java
│   │   ├── service/
│   │   │   └── CustomUserDetailsService.java
│   │   └── DemoSecurityApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── login.html
│       │   ├── register.html
│       │   ├── profile.html
│       │   ├── add-article.html
│       │   └── article-list.html
│       └── application.properties
```

## Installation et Utilisation

### Prérequis
- JDK 21
- Maven 3.6+
- IDE Java (IntelliJ IDEA recommandé)

### Installation

1. **Cloner le repository**
   ```bash
   git clone <url-du-repository>
   cd demoSecurity
   ```

2. **Choisir la branche du TP souhaité**
   ```bash
   # TP1a - Configuration basique
   git checkout main
   
   # TP1b - Login personnalisé
   git checkout TP1b
   
   # TP1c - Base de données
   git checkout TP1c
   
   # TP1d - Authentification complète
   git checkout TP1d
   ```

3. **Compiler et lancer l'application**
   ```bash
   mvn clean compile
   mvn spring-boot:run
   ```

### Accès à l'Application

- **Application** : http://localhost:8086
- **Console H2** (TP1c/TP1d) : http://localhost:8086/h2-console
    - JDBC URL : `jdbc:h2:mem:testdb`
    - Username : `sa`
    - Password : `password`

### Comptes de Test

**TP1a/TP1b** : Utilisateur généré automatiquement
- Username : `user`
- Password : Affiché dans la console au démarrage

**TP1d** : Utilisateurs créés au démarrage
- `matthieu@dev.com` / `password123`
- `alice@test.com` / `alice456`
- `bob@manager.com` / `bob789`
- `admin@system.com` / `admin999`

## Endpoints Disponibles

### API REST
- `GET /hello-public` - Endpoint public
- `GET /hello-private` - Endpoint protégé
- `GET /user-app` - Liste des utilisateurs (TP1c/TP1d)
- `POST /user-app/register` - Inscription utilisateur
- `GET /article` - Liste des articles (TP1c/TP1d)
- `POST /article/new` - Création d'article

### Pages Web
- `/login` - Page de connexion
- `/register` - Page d'inscription (TP1d)
- `/profile` - Profil utilisateur
- `/add-article` - Création d'article (TP1c/TP1d)
- `/article/list` - Liste des articles

## Tests et Validation

### Avec Navigateur
1. Accéder aux endpoints protégés
2. Vérifier les redirections vers login
3. Tester l'authentification
4. Valider les sessions utilisateur

### Avec Postman
1. Tests Basic Authentication
2. Gestion des en-têtes HTTP
3. Tests des API REST
4. Vérification des réponses JSON

### Base de Données (TP1c/TP1d)
1. Consulter les tables via H2 Console
2. Vérifier l'encodage des mots de passe
3. Valider la création d'entités
4. Contrôler les relations entre entités

## Points d'Attention Techniques

### Configuration Security
- Désactivation CSRF pour H2 Console
- Configuration `frameOptions().disable()` pour iframe H2
- Gestion des références circulaires dans l'injection de dépendances

### Encodage des Mots de Passe
- Utilisation de BCrypt pour le hashage
- Injection du `PasswordEncoder` par constructeur
- Validation en base de données

### Gestion des Erreurs
- Pages d'erreur personnalisées
- Messages de validation formulaires
- Gestion des exceptions d'authentification

## Progression Pédagogique

1. **TP1a** : Comprendre les mécanismes de base
2. **TP1b** : Personnaliser l'interface utilisateur
3. **TP1c** : Intégrer la persistance des données
4. **TP1d** : Créer un système complet d'authentification

Chaque TP s'appuie sur les concepts du précédent tout en introduisant de nouveaux éléments, permettant une montée en compétences progressive sur Spring Security.

## Ressources Utiles

- [Documentation Spring Security](https://docs.spring.io/spring-security/reference/)
- [Guide Spring Boot Security](https://spring.io/guides/gs/securing-web/)
- [Référence Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Documentation Thymeleaf](https://www.thymeleaf.org/documentation.html)