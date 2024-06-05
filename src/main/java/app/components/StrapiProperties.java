package app.components;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class StrapiProperties {

    private final String productUrl;
    private final String port;
    private final String url;
    private final String path;

    private StrapiProperties(@Value("${env.product-url}") String productUrl,
                             @Value("${env.strapi-port}") String port) {
        this.productUrl = productUrl;
        this.port = port;
        this.url = productUrl + ":" + port;
        this.path = "/api/";
    }
}
