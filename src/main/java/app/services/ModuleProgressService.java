package app.services;

import app.components.mappers.ModuleProgressMapper;
import app.domain.*;
import app.domain.Module;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;
import app.dtos.GetModuleProgressResponse;
import app.dtos.shared.Data;
import app.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ModuleProgressService {

    private final LessonProgressService lessonProgressService;
    private final ModuleProgressMapper moduleProgressMapper;
    private final PersonRepository personRepo;
    private final StepProgressService stepProgressService;

    /**
     * Constructs a ModuleProgressService with necessary repositories.
     *
     * @param moduleRepo Repository for accessing modules
     * @param statusRepo Repository for accessing statuses
     */
    public ModuleProgressService(LessonProgressService lessonProgressService,
                                 ModuleProgressMapper moduleProgressMapper,
                                 PersonRepository personRepo,
                                 StepProgressService stepProgressService) {
        this.lessonProgressService = lessonProgressService;
        this.moduleProgressMapper = moduleProgressMapper;
        this.personRepo = personRepo;
        this.stepProgressService = stepProgressService;
    }

    public ResponseEntity<Data<List<GetModuleProgressResponse>>> getAllStudied(Long personId) {
        Optional<Person> optPerson = personRepo.findById(personId);
        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            List<ModuleProgress> moduleProgresses = person.getModuleProgresses();
            List<GetModuleProgressResponse> studiedModules = new ArrayList<>();
            for (ModuleProgress moduleProgress : moduleProgresses) {
                GetModuleProgressResponse studiedModule =
                        getStudied(moduleProgress.getModule(), person);
                studiedModules.add(studiedModule);
            }
            Data<List<GetModuleProgressResponse>> data = new Data<>(studiedModules);
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public GetModuleProgressResponse getStudied(Module module, Person person) {
            List<Lesson> studiedLessons =
                    lessonProgressService.getAllStudied(module, person);
            Map<Lesson, List<StepProgress>> stepProgressMap =
                    stepProgressService.getAllStudied(studiedLessons, person);
        return moduleProgressMapper.toDto(module, stepProgressMap);
    }
}
