[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)
# JavaDA_PROJECT7: `Complétez votre backend pour rendre votre application plus sécurisée`

# spring-boot

## Technologies:

1. Framework: Spring Boot v2.6.9 
2. Maven 3.8.4
3. Java 17
4. Spring-Boot: 2.6.9
5. Thymeleaf
6. Bootstrap v.4.3.1
7. H2(Pour les tests)
8. MySQL 8.0.29
9. OAuth2 (Github)
## Démarrage d'Application
* Étape 1 : Cloner le code sur la branche develop_MySQL
* Étape 2 : Configurer application.properties (url, username et password -→ la connexion avec la BDD ou avec les variables d'environnement)
* Étape 3 : Créé une BDD avec MySQL (ou avec une autre, le nom: `poseidon_p7` par défaut),
puis exécuter les requêtes dans data.sql ou schema.sql dans le répertoire doc (sur la bdd de donnée créée à l'étape 3) pour créer les tables ou laisser l'application créer la bdd avec le JPA
* Étape 4 : Exécuter les requêtes dans data-h2.sql pour insérer les données
* Étape 5 : Démarrer l'app (Application.java), puis se rendre sur le navigateur puis saisir http://localhost:8085/ (par défaut: server.port=8085), puis cliquer le lien du login
* Étape 6 : Entrer un utilisateur(username) et son mot de passe (Note*)
## Se connecter avec GitHub 
* Étape 1 : enregister une nouvelle OAuth Application: Github-> Setting-> Developper Settings -> OAuthApps - New OAuth App -> enregistrer l'application Poseidon
* Étape 2 : Configurer le client-id et le client-secret générées dans application-dev_mysql.properties
## Démarrage avec le fichier Jar
Linge de commande ou avec un terminal exécuter : `mvn package` dans le répertoir racine du projet, puis exécuter `java -jar target/spring-boot-skeleton-0.0.1-SNAPSHOT.jar`
## Test Units et Test Integrations avec les rapports des tests Jacoco
### Test Units
![reportJacocoUTs6012023](https://user-images.githubusercontent.com/90509456/212762249-c253832f-86c6-4ddc-849f-17b967c01c1a.jpg)
### Test Integration
![reportJacocoITs16012023](https://user-images.githubusercontent.com/90509456/212762307-a04b06d4-82cc-4395-96c7-4a8eb4512bdf.jpg)
## Interfaces Web
### Page Home sans authentificaton
![homePageSansAuth](https://user-images.githubusercontent.com/90509456/212764162-4c55ce2a-6e52-45e0-a5c7-0da9f84d3a1e.jpg)
### Page Login personnalisé
![loginPage](https://user-images.githubusercontent.com/90509456/212764273-cd8f23c7-ae9f-458f-ae1b-8126e10288a7.jpg)
### Page Home avec authentification
![homeWithAuth](https://user-images.githubusercontent.com/90509456/212764380-7e5d178b-f614-4695-ac4e-1a6b7f0870ce.jpg)
### Page BidList 
![bidList](https://user-images.githubusercontent.com/90509456/212764520-24885927-bc32-49ed-864e-82cef28e2525.jpg)
### Page Add Bid avec validation 
![addBidList](https://user-images.githubusercontent.com/90509456/212764667-18a14c00-d5cb-4a14-8f02-4ac08b90ea46.jpg)
### Page UserList sans authorisation 
![userListSansAuth](https://user-images.githubusercontent.com/90509456/212764930-bc610be8-706e-4cb8-bbab-f9349edd499c.jpg)
### Page UserList with le rôle ADMIN
![adminPage](https://user-images.githubusercontent.com/90509456/212765062-f7cba9a2-6a8d-413e-b093-fe696331d6b0.jpg)
### Page Add New User avec validation (username, password)
![userValidation1](https://user-images.githubusercontent.com/90509456/212765982-9b116360-83db-4bca-a9fc-fed47e36e082.jpg) 
![userValidation2](https://user-images.githubusercontent.com/90509456/212765989-39e69579-b929-44de-bc0c-e68a3365c2ad.jpg)
### Page Update User avec validation (username, password)
![userValidation3](https://user-images.githubusercontent.com/90509456/212766261-af76aa9c-96bb-4d74-9151-723df5929b00.jpg)
![userValidation4](https://user-images.githubusercontent.com/90509456/212766265-04e7636d-9cde-421f-bea0-25d5c9933878.jpg)


### Note*: schema.sql et data-h2.sql se trouvent séparément dans le dossier doc et dans la racine du projet. Les mots de passe `Subhy7!`.
