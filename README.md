

# ![SmartShop](https://img.shields.io/badge/SmartShop-Backend-blue) SmartShop

## Contexte

SmartShop est une **application backend REST** pour MicroTech Maroc (distributeur B2B de matériel informatique).
Elle permet de gérer :

* Clients (statistiques, fidélité)
* Produits (CRUD avec soft delete)
* Commandes multi-produits
* Paiements multi-moyens et validation automatique


<img width="1902" height="935" alt="image" src="https://github.com/user-attachments/assets/9ab79074-16e8-4c72-a8d7-d3e207bf054d" />
<img width="1886" height="922" alt="image" src="https://github.com/user-attachments/assets/dd3ef2c1-d55f-4769-bef5-ef6080d2b0dc" />

---

## Fonctionnalités

### Clients

* CRUD complet des clients
* Suivi automatique : nombre de commandes, montant cumulé, date première/dernière commande
* Historique des commandes : id, date, montant TTC, statut

### Fidélité

* Niveaux : BASIC, SILVER, GOLD, PLATINUM
* Mise à jour automatique après chaque commande confirmée
* Application des remises selon le niveau et le sous-total

### Produits

* CRUD complet des produits
* Soft delete si commandes existantes
* Consultation avec filtres et pagination

### Commandes

* Création multi-produits avec validation de stock
* Calcul automatique : sous-total HT, remises, TVA (20%), total TTC
* Gestion des statuts : PENDING, CONFIRMED, CANCELED, REJECTED
* Décrémentation du stock et mise à jour du niveau fidélité

### Paiements

* Espèces, Chèque, Virement
* Paiement fractionné possible
* Validation des commandes uniquement si paiement total effectué

### Sécurité et rôles

* Authentification via **HTTP Session**
* Rôles : ADMIN (gestion complète) / CLIENT (consultation limitée)
* Permissions basées sur le rôle

---

## Stack Technique

* Java 8+ / Spring Boot
* Spring Data JPA / Hibernate
* PostgreSQL ou MySQL
* Lombok, MapStruct
* JUnit, Mockito
* REST API (JSON)

---

## Architecture

* **Controller** : expose les endpoints REST
* **Service** : logique métier
* **Repository** : accès aux données
* **DTO & Mapper** : conversion entités ↔ API
* **Entity** : modèles JPA

---

## Modèle de données

### Entités principales

* **User** : id, username, password, role
* **Client** : id, nom, email, niveau fidélité
* **Product** : id, nom, prix, stock
* **Commande** : id, client, articles, sous-total, remise, TVA, total, code promo, statut
* **OrderItem** : id, produit, quantité, prix unitaire, total ligne
* **Paiement** : id, commande, numéro, montant, type, date paiement, date encaissement, statut

### Enums

* `UserRole` : ADMIN / CLIENT
* `CustomerTier` : BASIC / SILVER / GOLD / PLATINUM
* `OrderStatus` : PENDING / CONFIRMED / CANCELED / REJECTED
* `PaymentStatus` : EN_ATTENTE / ENCAISSÉ / REJETÉ

---

## Installation

1. Cloner le dépôt :

```bash
git clone <lien_git>
cd SmartShop
```

2. Configurer la base de données dans `application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/smartshop
spring.datasource.username=postgres
spring.datasource.password=motdepasse
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Lancer l’application :

```bash
mvn spring-boot:run
```

4. Tester l’API via Postman ou Swagger (`http://localhost:8080/swagger-ui.html` si configuré).

---

## Utilisation

* Endpoints sous `/api/...`
* ADMIN : CRUD complet + validation/annulation/rejet commandes
* CLIENT : consultation de ses informations, historique commandes et produits

---

## Tests

* Unitaires avec **JUnit & Mockito**
* Couverture pour les services principaux (ClientService, ProductService, OrderService)

---
![CLLS](https://github.com/user-attachments/assets/74dfd369-c36a-4d00-935f-9a4f603c78f2)


---

## Badges recommandés (exemple)

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![License](https://img.shields.io/badge/license-MIT-yellow)

---


