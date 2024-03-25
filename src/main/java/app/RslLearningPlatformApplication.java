package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// Swagger documentation: http://localhost:8080/swagger-ui/index.html

@SpringBootApplication
public class RslLearningPlatformApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RslLearningPlatformApplication.class, args);
    }

}
