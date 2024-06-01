package app.controllers;

import app.dtos.GetModuleProgressResponse;
import app.services.ModuleProgressService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "module-progresses",
                produces = "application/json")
public class ModuleProgressController {

    private final ModuleProgressService moduleProgressService;

    public ModuleProgressController(ModuleProgressService moduleProgressService) {
        this.moduleProgressService = moduleProgressService;
    }

    @GetMapping(path = "/{personId}")
    public ResponseEntity<List<GetModuleProgressResponse>> getAll(@PathVariable("personId") Long personId) {
        return moduleProgressService.getAll(personId);
    }
}
