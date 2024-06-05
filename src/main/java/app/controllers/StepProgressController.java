package app.controllers;

import app.dtos.GetModuleProgressResponse;
import app.services.StepService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "step-progresses",
                produces = "application/json")
public class StepProgressController {

    private final StepService stepService;

    public StepProgressController(StepService stepService) {
        this.stepService = stepService;
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<GetModuleProgressResponse> post(
            @RequestParam Long personId,
            @RequestParam Long moduleId,
            @RequestParam Long lessonId,
            @RequestParam Long stepId) {
        return stepService.post(personId, moduleId, lessonId, stepId);
    }
}
