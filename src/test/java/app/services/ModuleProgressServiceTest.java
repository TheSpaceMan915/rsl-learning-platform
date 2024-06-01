package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleProgressServiceTest {

    @Autowired
    private ModuleProgressService moduleProgressService;

    @Test
    public void getAll() {
        moduleProgressService.getAll(1L);
    }
}
