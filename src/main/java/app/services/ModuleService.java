package app.services;

import app.dtos.GetModuleRequest;
import app.dtos.GetModulesRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the retrieval of module content from an external content management system (CMS), specifically Strapi.
 * This service class is part of the business logic layer and interacts with the CMS to fetch the content that will be used in modules on the educational platform.
 *
 * <p>Implements the {@link ContentConstants} interface to ensure a standardized method for fetching content.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class ModuleService {

    private final RestTemplate rest;

    /**
     * Constructs a new ModuleService with the provided RestTemplate.
     * The RestTemplate is configured externally and injected to enable communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, must not be null
     */
    public ModuleService(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Retrieves all content from the CMS related to modules.
     * This method constructs an HTTP GET request to fetch the module content.
     *
     * @return a {@link GetModulesRequest} data transfer object containing the module content from the CMS.
     */
    public GetModulesRequest getAllContent() {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("modules")
                .queryParam("publicationState", "{publicationState}")
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("publicationState", "preview");
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetModulesRequest> response = rest.getForEntity(
                urlTemplate,
                GetModulesRequest.class,
                params);
        return response.getBody();
    }

    public GetModuleRequest getContentById(String id) {
//        Construct the URL for the request using URI Components Builder
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("modules", id)
                .queryParam("populate", "{populate}")
                .encode()
                .toUriString();

//        Define parameters for the query
        Map<String, String> params = new HashMap<>();
        params.put("populate", "*");

//        Execute the GET request
        ResponseEntity<GetModuleRequest> response = rest.getForEntity(
                urlTemplate,
                GetModuleRequest.class,
                params);
        return response.getBody();
    }
}
