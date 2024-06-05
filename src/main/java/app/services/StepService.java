package app.services;

import app.components.StrapiProperties;
import app.components.mappers.StepMapper;
import app.domain.*;
import app.domain.Module;
import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;
import app.dtos.GetModuleProgressResponse;
import app.dtos.GetStepRequest;
import app.dtos.GetStepResponse;
import app.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * Provides operations to fetch step content from an external content management system (CMS), specifically Strapi.
 * This service class facilitates the business logic related to the retrieval of step details necessary for the educational platform.
 *
 * <p>This service implements the {@link StrapiProperties} interface, which standardizes the way content is fetched across different service classes.</p>
 *
 * @author Nikita Kolychev
 */
@Service
public class StepService {

    private final RestTemplate rest;
    private final StepMapper stepMapper;
    private final StrapiProperties strapiProperties;
    private final PersonRepository personRepo;
    private final LessonRepository lessonRepo;
    private final ModuleRepository moduleRepo;
    private final StatusRepository statusRepo;
    private final LessonProgressRepository lessonProgressRepo;
    private final ModuleProgressRepository moduleProgressRepo;
    private final StepProgressRepository stepProgressRepo;
    private final ModuleProgressService moduleProgressService;

    /**
     * Constructs a new StepService with the given RestTemplate.
     * The RestTemplate is configured externally and injected here to facilitate HTTP communication with the CMS.
     *
     * @param rest the RestTemplate used for HTTP operations, not null
     */
    public StepService(RestTemplate rest,
                       StepMapper stepMapper,
                       StrapiProperties strapiProperties,
                       PersonRepository personRepo,
                       LessonRepository lessonRepo,
                       ModuleRepository moduleRepo,
                       StatusRepository statusRepo,
                       LessonProgressRepository lessonProgressRepo,
                       ModuleProgressRepository moduleProgressRepo,
                       StepProgressRepository stepProgressRepo,
                       ModuleProgressService moduleProgressService) {
        this.rest = rest;
        this.stepMapper = stepMapper;
        this.strapiProperties = strapiProperties;
        this.personRepo = personRepo;
        this.lessonRepo = lessonRepo;
        this.moduleRepo = moduleRepo;
        this.statusRepo = statusRepo;
        this.lessonProgressRepo = lessonProgressRepo;
        this.moduleProgressRepo = moduleProgressRepo;
        this.stepProgressRepo = stepProgressRepo;
        this.moduleProgressService = moduleProgressService;
    }

    public ResponseEntity<GetStepResponse> getById(Long id) {
//        Construct the URL for the request using URI Components Builder
        String url = UriComponentsBuilder.fromHttpUrl(strapiProperties.getUrl())
                .path(strapiProperties.getPath())
                .pathSegment("steps", Long.toString(id))
                .queryParam("populate", "{populate}")
                .buildAndExpand("*")
                .toUriString();

//        Execute the GET request
        ResponseEntity<GetStepRequest> responseEntity = rest.getForEntity(
                url,
                GetStepRequest.class);
        if (responseEntity.hasBody()) {
            GetStepResponse step =
                    stepMapper.toStep(responseEntity.getBody());
            return new ResponseEntity<>(step, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    Adds a new Step record and a corresponding StepProgress record
    @Transactional
    public ResponseEntity<GetModuleProgressResponse> post(
            Long personId,
            Long moduleId,
            Long lessonId,
            Long stepId) {
        Optional<Person> optPerson = personRepo.findById(personId);
        Optional<Module> optModule = moduleRepo.findById(moduleId);
        Optional<Lesson> optLesson = lessonRepo.findById(lessonId);

//        Check if there are needed Modules and Lessons
//        to create a new Step and corresponding Progress records
        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            Status finished = statusRepo.findByName("Finished").orElseThrow();
            if (optModule.isEmpty()) {
                Module newModule = new Module(moduleId);
                Lesson newLesson = new Lesson(lessonId);
                Step newStep = new Step(stepId);
                newLesson.addStep(newStep);
                newModule.addLesson(newLesson);

                newModule = moduleRepo.save(newModule);
                moduleProgressRepo.save(new ModuleProgress(person, newModule));
                lessonProgressRepo.save(new LessonProgress(person, newLesson));
                stepProgressRepo.save(new StepProgress(person, newStep, finished));
                GetModuleProgressResponse response =
                        moduleProgressService.getStudied(newModule, person);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else if (optLesson.isPresent()) {
                Module oldModule = optModule.get();
                Lesson oldLesson = optLesson.get();
                Step newStep = new Step(stepId);
                oldLesson.addStep(newStep);

                oldLesson = lessonRepo.save(oldLesson);
                stepProgressRepo.save(new StepProgress(person, newStep, finished));
                GetModuleProgressResponse response =
                        moduleProgressService.getStudied(oldModule, person);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else {
                Module oldModule = optModule.get();
                Lesson newLesson = new Lesson(lessonId);
                oldModule.addLesson(newLesson);
                Step newStep = new Step(stepId);
                newLesson.addStep(newStep);

                oldModule = moduleRepo.save(oldModule);
                lessonProgressRepo.save(new LessonProgress(person, newLesson));
                stepProgressRepo.save(new StepProgress(person, newStep, finished));
                GetModuleProgressResponse response =
                        moduleProgressService.getStudied(oldModule, person);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
