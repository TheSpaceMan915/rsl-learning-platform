package app.controllers;

import app.dtos.GetLessonResponse;
import app.services.LessonService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/lessons",
                produces = "application/json")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetLessonResponse> getById(@PathVariable("id") Long id) {
        return lessonService.getById(id);
    }
}
