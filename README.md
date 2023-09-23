# spiralin.com
![Application logo](https://avatars.githubusercontent.com/u/145035306?s=200&v=4)

## Description
> All-in-one dropshipping automation tool.

## Technologies used
[<img src="https://upload.wikimedia.org/wikipedia/fr/2/2e/Java_Logo.svg" width=70 height=70 />](https://dev.java/learn/language-basics/variables/)
[<img src="https://spring.io/img/spring.svg" width=70 height=70 />](https://docs.spring.io/spring-boot/docs/current/reference/html/)
[<img src="http://mongodb-js.github.io/leaf/mongodb-leaf_128x128.png" width=70 height=70 />](https://www.mongodb.com/)

- Front-End : [Coming soon](/)
- Back-End : [Spring Boot](https://spring.io/projects/spring-boot)
- Base de données : [MongoDB](https://www.mongodb.com/fr)

## Prerequisites
Before you start, make sure you have the following items installed on your system:

- Java JDK 18 or higher : [Download here](https://www.oracle.com/java/technologies/javase-downloads.html)
- Docker : [Download here](https://www.docker.com/get-started)
- MongoDB : [Download here](https://www.mongodb.com/try/download/community)
- Maven : [Download here](https://maven.apache.org/download.cgi)

## Installation
1. Clone this repository :
   ```shell
   git clone git@github.com:spiralinapp/spiralin.com.git
   cd spiralin.com

2. Start the application with Docker :
   ```shell
   docker-compose up

3. Compile the project with Maven :
   ```shell
   mvn clean install

The application should now be running and accessible at http://localhost:8080.

## Configuration
You can configure the application by editing the application.properties file in the src/main/resources directory. Be sure to update the MongoDB connection information, ports, etc., as required.

## Use
[Documentation Swagger](http://localhost:8080/swagger-ui/index.html)

## Licence
Ce projet est sous licence MIT.