package app.services;

import app.components.mappers.LessonMapper;
import app.dtos.GetLessonRequest;
import app.dtos.GetLessonResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
    private final LessonMapper mapper;

    /**
     * Constructs a new LessonService with the given RestTemplate.
     * The RestTemplate is configured externally and injected here to facilitate HTTP communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, not null
     */
    public LessonService(RestTemplate rest,
                         LessonMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    public ResponseEntity<GetLessonResponse> getContentById(Long id) {
//        Construct the URL for the request using URI Components Builder
        String url = UriComponentsBuilder.fromHttpUrl(ContentConstants.BASE_URL)
                .path(ContentConstants.BASE_PATH)
                .pathSegment("lectures", Long.toString(id))
                .queryParam("populate[steps][populate]", "{populate[steps][populate]}")
                .queryParam("populate[steps][populate]", "{populate[steps][populate]}")
                .queryParam("populate[status][populate]", "{populate[status][populate]}")
                .buildAndExpand("status", "type", "*")
                .toUriString();

//        Execute the GET request
        ResponseEntity<GetLessonRequest> responseEntity = rest.getForEntity(
                url,
                GetLessonRequest.class);
        if (responseEntity.hasBody()) {
            GetLessonResponse lesson =
                    mapper.toLesson(responseEntity.getBody());
            return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
}
