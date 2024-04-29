package app.controllers;

import app.dtos.GetModuleResponse;
import app.services.ModuleService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/modules",
                produces = "application/json")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<GetModuleResponse> getById(@PathVariable("id") Long id) {
        return moduleService.getById(id);
    }

    @GetMapping
    public ResponseEntity<List<GetModuleResponse>> getAll() {
        return moduleService.getAll();
    }
}
