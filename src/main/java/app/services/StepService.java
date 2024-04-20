package app.services;

import app.components.mappers.StepMapper;
import app.dtos.GetStepRequest;
import app.dtos.GetStepResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    private final StepMapper stepMapper;

    /**
     * Constructs a new StepService with the given RestTemplate.
     * The RestTemplate is configured externally and injected here to facilitate HTTP communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, not null
     */
    public StepService(RestTemplate rest,
                       StepMapper stepMapper) {
        this.rest = rest;
        this.stepMapper = stepMapper;
    }

    public ResponseEntity<GetStepResponse> getContentById(Long id) {
//        Construct the URL for the request using URI Components Builder
        String url = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("steps", Long.toString(id))
                .queryParam("populate", "{populate}")
                .buildAndExpand("*")
                .toUriString();

//        Execute the GET request
        ResponseEntity<GetStepRequest> responseEntity = rest.getForEntity(
                url,
                GetStepRequest.class);
        if (responseEntity.hasBody()) {
            GetStepResponse step =
                    stepMapper.toStep(responseEntity.getBody());
            return new ResponseEntity<>(step, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
