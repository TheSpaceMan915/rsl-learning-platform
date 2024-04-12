package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @Test
    public void getAllContent() {
        lessonService.getAllContent();
    }
}
