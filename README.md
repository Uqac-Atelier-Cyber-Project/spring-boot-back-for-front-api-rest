
# spring-boot-back-for-front-api-rest

## Description

Ce projet est une API REST développée avec Spring Boot. Elle sert de backend pour une application front, permettant de gérer des rapports et d'exécuter diverses analyses.

## Prérequis

- Java 21 ou supérieur
- Maven 3.6.3 ou supérieur
- Une base de données SQL (par exemple, MySQL, PostgreSQL)

## Installation

1. Clonez le dépôt :
   ```bash
   git clone https://github.com/Uqac-Atelier-Cyber-Project/spring-boot-back-for-front-api-rest.git
   cd spring-boot-back-for-front-api-rest
   ```

2. Configurez la base de données dans le fichier `application.properties` :
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/votre_base_de_donnees
   spring.datasource.username=votre_nom_utilisateur
   spring.datasource.password=votre_mot_de_passe
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Compilez et packagez l'application avec Maven :
   ```bash
   mvn clean install
   ```

4. Démarrez l'application :
   ```bash
   mvn spring-boot:run
   ```

## Utilisation

L'API expose plusieurs endpoints pour gérer les rapports et exécuter des analyses. Voici quelques exemples d'utilisation :

- **Générer un rapport** :
  ```http
  POST /reportGenerate/generate
  Content-Type: application/json

  {
      "reportId": 123
  }
  ```

- **Récupérer les rapports disponibles pour un utilisateur** :
  ```http
  POST /reportAvailable
  Content-Type: application/json

  {
      "userId": "uuid-de-l-utilisateur"
  }
  ```

- **Marquer un rapport comme lu** :
  ```http
  POST /reportRead
  Content-Type: application/json

  {
      "reportId": 123,
      "userId": "uuid-de-l-utilisateur"
  }
  ```

## Dépendances

Le projet utilise les dépendances suivantes :

- Spring Boot
- Spring Data JPA
- Spring Web
- MySQL Connector (ou autre connecteur de base de données)
- JacksonJSON
