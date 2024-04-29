package app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String baseUrl;
    private final String frontendPort;
    private final String apiPath;
    private final String pathPattern;
    private final String corsOrigin;

    public WebConfig(@Value("${env.base-url}") String baseUrl,
                     @Value("${env.frontend-port}") String frontendPort,
                     @Value("${env.api-path}") String apiPath) {
        this.baseUrl = baseUrl;
        this.frontendPort = frontendPort;
        this.apiPath = apiPath;
        this.corsOrigin = baseUrl + ":" + frontendPort;
        this.pathPattern = apiPath + "/**";
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(apiPath,
                HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(pathPattern)
                .allowedOrigins(corsOrigin)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}