package app.services;

import app.components.StrapiProperties;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
@Slf4j
public class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StrapiProperties strapiProperties;

//    @Test
//    public void getAllContent() {
//        lessonService.getAllContent();
//    }

    @Test
    public void buildUrl() {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(strapiProperties.getUrl())
                .path(strapiProperties.getPath())
                .pathSegment("lectures", "1")
                .queryParam("populate[steps][populate]", "{populate[steps][populate]}")
                .queryParam("populate[steps][populate]", "{populate[steps][populate]}")
                .queryParam("populate[status][populate]", "{populate[status][populate]}")
                .buildAndExpand("status", "type", "*")
                .toUriString();
        log.info("URL: {}", urlTemplate);
    }

    @Test
    public void getById() {
        lessonService.getById(1L);
    }
}
