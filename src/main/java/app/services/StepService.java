package app.services;

import app.dtos.unique.StepContent;
import app.services.interfaces.Contained;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class StepService implements Contained<StepContent> {

    private RestTemplate rest;

    public StepService(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public StepContent getAllContent() {

//        Set up a request
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(Contained.BASE_URL)
                .path("/api/")
                .pathSegment("steps")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Send the request
        ResponseEntity<StepContent> response = rest.getForEntity(
                urlTemplate,
                StepContent.class,
                params);
        return response.getBody();
    }
}
