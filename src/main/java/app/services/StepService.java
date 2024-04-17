package app.services;

import app.dtos.GetStepRequest;
import app.dtos.GetStepsRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides operations to fetch step content from an external content management system (CMS), specifically Strapi.
 * This service class facilitates the business logic related to the retrieval of step details necessary for the educational platform.
 *
 * <p>This service implements the {@link ContentConstants} interface, which standardizes the way content is fetched across different service classes.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class StepService {

    private final RestTemplate rest;

    /**
     * Constructs a new StepService with the given RestTemplate.
     * The RestTemplate is configured externally and injected here to facilitate HTTP communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, not null
     */
    public StepService(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Retrieves all available content for steps from the CMS.
     * This method constructs an HTTP GET request that is pre-configured to fetch step content.
     *
     * @return a {@link GetStepsRequest} DTO containing the fetched step content from the CMS.
     */
    public GetStepsRequest getAllContent() {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("steps")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetStepsRequest> response = rest.getForEntity(
                urlTemplate,
                GetStepsRequest.class,
                params);
        return response.getBody();
    }

    public GetStepRequest getContentById(String id) {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("steps", id)
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetStepRequest> response = rest.getForEntity(
                urlTemplate,
                GetStepRequest.class,
                params);
        return response.getBody();
    }
}
