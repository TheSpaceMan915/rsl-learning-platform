package app.services;

import app.dtos.GetLessonRequest;
import app.dtos.GetLessonsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides operations to fetch lesson content from an external content management system (CMS), specifically Strapi.
 * This service class facilitates the business logic related to the retrieval of lesson details necessary for the educational platform.
 *
 * <p>This service implements the {@link ContentConstants} interface, which standardizes the way content is fetched across different service classes.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class LessonService {

    private final RestTemplate rest;

    /**
     * Constructs a new LessonService with the given RestTemplate.
     * The RestTemplate is configured externally and injected here to facilitate HTTP communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, not null
     */
    public LessonService(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Retrieves all available content for lessons from the CMS.
     * This method constructs an HTTP GET request that is pre-configured to fetch lesson content.
     *
     * @return a {@link GetLessonsRequest} DTO containing the fetched lesson content from the CMS.
     */
    public GetLessonsRequest getAllContent() {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("lectures")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetLessonsRequest> response = rest.getForEntity(
                urlTemplate,
                GetLessonsRequest.class,
                params);
        return response.getBody();
    }

    public GetLessonRequest getContentById(String id) {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("lectures", id)
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetLessonRequest> response = rest.getForEntity(
                urlTemplate,
                GetLessonRequest.class,
                params);
        return response.getBody();
    }
}
