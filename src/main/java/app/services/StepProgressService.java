package app.services;

import app.domain.*;
import app.domain.Module;
import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;
import app.domain.progress.ids.LessonProgressId;
import app.domain.progress.ids.ModuleProgressId;
import app.dtos.GetModuleProgressResponse;
import app.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StepProgressService {

    private final PersonRepository personRepo;
    private final ModuleRepository moduleRepo;
    private final LessonRepository lessonRepo;
    private final StepRepository stepRepo;
    private final StatusRepository statusRepo;
    private final ModuleProgressRepository moduleProgressRepo;
    private final LessonProgressRepository lessonProgressRepo;
    private final StepProgressRepository stepProgressRepo;
    private final ModuleProgressService moduleProgressService;

    /**
     * Constructs a new StepProgressService with necessary repositories for managing steps and statuses.
     *
     * @param stepRepo Repository for accessing step data
     * @param statusRepo Repository for accessing status data
     */
    public StepProgressService(PersonRepository personRepo,
                               ModuleRepository moduleRepo,
                               LessonRepository lessonRepo,
                               StepRepository stepRepo,
                               StatusRepository statusRepo,
                               ModuleProgressRepository moduleProgressRepo,
                               LessonProgressRepository lessonProgressRepo,
                               StepProgressRepository stepProgressRepo,
                               ModuleProgressService moduleProgressService) {
        this.personRepo = personRepo;
        this.moduleRepo = moduleRepo;
        this.lessonRepo = lessonRepo;
        this.stepRepo = stepRepo;
        this.statusRepo = statusRepo;
        this.moduleProgressRepo = moduleProgressRepo;
        this.lessonProgressRepo = lessonProgressRepo;
        this.stepProgressRepo = stepProgressRepo;
        this.moduleProgressService = moduleProgressService;
    }

//    Finds studied Steps from the Lessons
    public Map<Lesson, List<StepProgress>> getAllStudied(
            List<Lesson> lessons,
            Person person) {
        Map<Lesson, List<StepProgress>> stepProgressMap = new HashMap<>();
        for (Lesson lesson : lessons) {
            List<StepProgress> stepProgresses = new ArrayList<>();
            for (Step step : lesson.getSteps()) {
                Optional<StepProgress> optStepProgress =
                        stepProgressRepo.findByIdPersonIdAndIdStepId(
                                person.getId(), step.getId());
                optStepProgress.ifPresent(stepProgresses::add);
            }
            if (!stepProgresses.isEmpty()) {
                stepProgressMap.put(lesson, stepProgresses);
            }
        }
        return stepProgressMap;
    }

//    TODO: Divide into separate service functions
    public ResponseEntity<GetModuleProgressResponse> postById(
            Long personId,
            Long moduleId,
            Long lessonId,
            Long stepId) {
        Optional<Person> optPerson = personRepo.findById(personId);
        Optional<Module> optModule = moduleRepo.findById(moduleId);
        Optional<Lesson> optLesson = lessonRepo.findById(lessonId);

//        Check if there are needed Modules and Lessons
//        to create a new Step
        if (optPerson.isPresent()) {

            if (optModule.isPresent()) {
                Module oldModule = optModule.get();

                if (optLesson.isPresent()) {
                    Lesson oldLesson = optLesson.get();
                    Step newStep = new Step(stepId);
                    oldLesson.addStep(newStep);
                    lessonRepo.save(oldLesson);
                }
                Lesson newLesson = new Lesson(lessonId);
                Step newStep = new Step(stepId);
                newLesson.addStep(newStep);
                lessonRepo.save(newLesson);
            }
            Module newModule = moduleRepo.save(new Module(moduleId));
            Lesson newLesson = new Lesson(lessonId);
            Step newStep = new Step(stepId);
            newLesson.addStep(newStep);
            newModule.addLesson(newLesson);
            moduleRepo.save(newModule);

            Person person = optPerson.get();
            Module module = moduleRepo.findById(moduleId).orElseThrow();
            Lesson lesson = lessonRepo.findById(lessonId).orElseThrow();
            Step step = stepRepo.findById(stepId).orElseThrow();
            Status status = statusRepo.findByName("Finished").orElseThrow();

//        Check if there are needed Module and Lesson Progresses
//        to create a new Step Progress
            Optional<ModuleProgress> optModuleProgress =
                    moduleProgressRepo.findById(new ModuleProgressId(personId, moduleId));
            if (optModuleProgress.isPresent()) {
                ModuleProgress moduleProgress = optModuleProgress.get();
                Optional<LessonProgress> optLessonProgress =
                        lessonProgressRepo.findById(new LessonProgressId(personId, lessonId));

                if (optLessonProgress.isPresent()) {
                    stepProgressRepo.save(new StepProgress(person, step, status));
                    GetModuleProgressResponse response =
                            moduleProgressService.getStudied(module, person);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                lessonProgressRepo.save(new LessonProgress(person, lesson));
                stepProgressRepo.save(new StepProgress(person, step, status));
                GetModuleProgressResponse response =
                        moduleProgressService.getStudied(module, person);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ModuleProgress newModuleProgress =
                    moduleProgressRepo.save(new ModuleProgress(person, module));
            lessonProgressRepo.save(new LessonProgress(person, lesson));
            stepProgressRepo.save(new StepProgress(person, step, status));
            GetModuleProgressResponse response =
                    moduleProgressService.getStudied(module, person);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
