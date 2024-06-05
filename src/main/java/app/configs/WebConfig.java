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

    private final String devUrl;
    private final String devPort;
    private final String productUrl;
    private final String productPort;
    private final String apiPath;
    private final String pathPattern;
    private final String cors1;
    private final String cors2;
    private final String cors3;
    private final String cors4;

    public WebConfig(@Value("${env.dev-url}") String devUrl,
                     @Value("${env.dev-port}") String devPort,
                     @Value("${env.product-url}") String productUrl,
                     @Value("${env.product-port}") String productPort,
                     @Value("${env.api-path}") String apiPath) {
        this.devUrl = devUrl;
        this.devPort = devPort;
        this.productUrl = productUrl;
        this.productPort = productPort;
        this.apiPath = apiPath;
        this.pathPattern = apiPath + "/**";
        this.cors1 = devUrl + ":" + devPort;
        this.cors2 = productUrl + ":" + productPort;
        this.cors3 = devUrl + ":" + productPort;
        this.cors4 = productUrl + ":" + devPort;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(apiPath,
                HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(pathPattern)
                .allowedOrigins(cors1)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);

        registry.addMapping(pathPattern)
                .allowedOrigins(cors2)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);

        registry.addMapping(pathPattern)
                .allowedOrigins(cors3)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);

        registry.addMapping(pathPattern)
                .allowedOrigins(cors4)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}