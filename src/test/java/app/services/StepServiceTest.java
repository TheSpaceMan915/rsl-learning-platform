package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StepServiceTest {

    @Autowired
    private StepService stepService;

    @Test
    public void getById() {
        stepService.getById(4L);
    }

//    TODO: Run the test and check that the logic of the algorithm is correct
    @Test
    public void post() {
        stepService.post(1L, 3532L, 7324L, 34L);
    }
}
