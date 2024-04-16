package app.services;

import app.dtos.unique.GetStepRequest;
import app.services.interfaces.Contained;

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
 * <p>This service implements the {@link Contained} interface, which standardizes the way content is fetched across different service classes.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class StepService implements Contained<GetStepRequest> {

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
     * @return a {@link GetStepRequest} DTO containing the fetched step content from the CMS.
     */
    @Override
    public GetStepRequest getAllContent() {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(Contained.BASE_URL)
                .path("/api/")
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
        ResponseEntity<GetStepRequest> response = rest.getForEntity(
                urlTemplate,
                GetStepRequest.class,
                params);
        return response.getBody();
    }
}
