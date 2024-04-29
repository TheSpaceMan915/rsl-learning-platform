package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleServiceTest {

    @Autowired
    private ModuleService moduleService;

    @Test
    public void getAll() {
        moduleService.getAll();
    }

    @Test
    public void getById() {
        moduleService.getById(1L);
    }
}
