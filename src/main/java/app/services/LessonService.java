package app.services;

import app.dtos.unique.LessonContent;
import app.services.interfaces.Contained;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class LessonService implements Contained<LessonContent> {

    private RestTemplate rest;

    public LessonService(RestTemplate rest) {
        this.rest = rest;
    }

    @Override
    public LessonContent getAllContent() {

//        Set up a request
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(Contained.BASE_URL)
                .path("/api/")
                .pathSegment("lectures")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Send the request
        ResponseEntity<LessonContent> response = rest.getForEntity(
                urlTemplate,
                LessonContent.class,
                params);
        return response.getBody();
    }
}
