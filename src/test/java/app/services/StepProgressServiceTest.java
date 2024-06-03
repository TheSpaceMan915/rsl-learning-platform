package app.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StepProgressServiceTest {

    @Autowired
    private StepProgressService stepProgressService;

    @Test
    public void postStepProgress() {
        stepProgressService.postStepProgress(1L, 32L, 53L, 34L);
    }
}
