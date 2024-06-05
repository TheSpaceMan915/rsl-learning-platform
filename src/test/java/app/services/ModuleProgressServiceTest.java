package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModuleProgressServiceTest {

    @Autowired
    private ModuleProgressService moduleProgressService;

//    TODO: Run the test and check that getAllStudied StepProgresses works well
    @Test
    public void getAllStudied() {
        moduleProgressService.getAllStudied(1L);
    }
}
