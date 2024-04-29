package app.components;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StrapiProperties {

    private final String baseUrl;
    private final String port;
    private final String url;
    private final String path;

    private StrapiProperties(@Value("${env.base-url}") String baseUrl,
                             @Value("${env.strapi-port}") String port) {
        this.baseUrl = baseUrl;
        this.port = port;
        this.url = baseUrl + ":" + port;
        this.path = "/api/";
    }
}
