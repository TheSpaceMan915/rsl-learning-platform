package app.services;

import app.dtos.unique.ModuleContent;
import app.services.interfaces.Contained;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModuleService implements Contained<ModuleContent> {

    private RestTemplate rest;

    public ModuleService(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public ModuleContent getAllContent() {

//        Set up a request
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(Contained.BASE_URL)
                .path("/api/")
                .pathSegment("modules")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Send the request
        ResponseEntity<ModuleContent> response = rest.getForEntity(
                urlTemplate,
                ModuleContent.class,
                params);
        return response.getBody();
    }
}
