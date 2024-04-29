package app.controllers;

import app.dtos.GetStepResponse;
import app.services.StepService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/steps",
                produces = "application/json")
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetStepResponse> getById(@PathVariable("id") Long id) {
        return stepService.getById(id);
    }
}
