package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StepServiceTest {

    @Autowired
    private StepService stepService;

//    @Test
//    public void getAllContent() {
//        stepService.getAllContent();
//    }

    @Test
    public void getById() {
        stepService.getById(4L);
    }
}
