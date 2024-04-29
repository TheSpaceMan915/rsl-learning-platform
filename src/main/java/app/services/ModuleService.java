package app.services;

import app.components.StrapiProperties;
import app.components.mappers.ModuleMapper;
import app.dtos.GetModuleRequest;
import app.dtos.GetModuleResponse;
import app.dtos.GetModulesRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the retrieval of module content from an external content management system (CMS), specifically Strapi.
 * This service class is part of the business logic layer and interacts with the CMS to fetch the content that will be used in modules on the educational platform.
 *
 * <p>Implements the {@link StrapiProperties} interface to ensure a standardized method for fetching content.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class ModuleService {

    private final RestTemplate rest;
    private final ModuleMapper moduleMapper;
    private final StrapiProperties strapiProperties;

    /**
     * Constructs a new ModuleService with the provided RestTemplate.
     * The RestTemplate is configured externally and injected to enable communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, must not be null
     */
    public ModuleService(RestTemplate rest,
                         ModuleMapper moduleMapper,
                         StrapiProperties strapiProperties) {
        this.rest = rest;
        this.moduleMapper = moduleMapper;
        this.strapiProperties = strapiProperties;
    }

    /**
     * Retrieves all content from the CMS related to modules.
     * This method constructs an HTTP GET request to fetch the module content.
     *
     * @return a {@link GetModulesRequest} data transfer object containing the module content from the CMS.
     */
    public ResponseEntity<List<GetModuleResponse>> getAll() {
//        Construct the URL for the request using URI Components Builder
        String url = UriComponentsBuilder.fromHttpUrl(strapiProperties.getUrl())
                .path(strapiProperties.getPath())
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
                url,
                GetModulesRequest.class,
                params);
        if (response.hasBody()) {
            List<GetModuleResponse> modules =
                    moduleMapper.toModules(response.getBody());
            return new ResponseEntity<>(modules, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<GetModuleResponse> getById(Long id) {
//        Construct the URL for the request using URI Components Builder
        String url = UriComponentsBuilder.fromHttpUrl(strapiProperties.getUrl())
                .path(strapiProperties.getPath())
                .pathSegment("modules", Long.toString(id))
                .queryParam("populate[lectures][populate]", "{populate[lectures][populate]}")
                .queryParam("populate[status][populate]", "{populate[status][populate]}")
                .buildAndExpand("status", "*")
                .toUriString();

//        Execute the GET request
        ResponseEntity<GetModuleRequest> responseEntity = rest.getForEntity(
                url,
                GetModuleRequest.class);
        if (responseEntity.hasBody()) {
            GetModuleResponse module =
                    moduleMapper.toModule(responseEntity.getBody());
            return new ResponseEntity<>(module, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
