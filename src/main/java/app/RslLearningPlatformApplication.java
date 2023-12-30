package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO:
// Create "data" and "requests" folders in the main folder of the project
// Create "configs" folder in "app" folder
// Log HTTP requests with Actuator, don't forget to secure the endpoints
// Write a Dockerfile for the app
// Create another branch and use Spring Data JDBC instead of Spring Data JPA

// Swagger documentation: http://localhost:8080/swagger-ui/index.html

@SpringBootApplication
public class RslLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(RslLearningPlatformApplication.class, args);
    }

}
