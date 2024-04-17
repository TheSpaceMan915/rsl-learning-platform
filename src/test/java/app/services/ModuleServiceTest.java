package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleServiceTest {

    @Autowired
    private ModuleService moduleService;

    @Test
    public void getAllContent() {
        moduleService.getAllContent();
    }

    @Test
    public void getContentById() {
        moduleService.getContentById("1");
    }
}
