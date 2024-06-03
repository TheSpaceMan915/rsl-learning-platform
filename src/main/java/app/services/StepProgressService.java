package app.services;

import app.components.mappers.ModuleProgressMapper;
import app.domain.*;
import app.domain.Module;
import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.Progress;
import app.domain.progress.StepProgress;
import app.domain.progress.ids.LessonProgressId;
import app.domain.progress.ids.ModuleProgressId;
import app.dtos.GetModuleProgressResponse;
import app.repositories.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing step progress within the educational platform.
 * This class handles the creation and updating of step progress entries for individuals.
 *
 * <p>Implements the {@link Progressive} interface, which defines standard operations for managing progress entries.
 *
 * @author  Nikita Kolychev
 */
@Service
public class StepProgressService implements Progressive {

    private final PersonRepository personRepo;
    private final ModuleRepository moduleRepo;
    private final LessonRepository lessonRepo;
    private final StepRepository stepRepo;
    private final StatusRepository statusRepo;
    private final ModuleProgressRepository moduleProgressRepo;
    private final LessonProgressRepository lessonProgressRepo;
    private final StepProgressRepository stepProgressRepo;
    private final ModuleProgressMapper moduleProgressMapper;

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
                               ModuleProgressMapper moduleProgressMapper) {
        this.personRepo = personRepo;
        this.moduleRepo = moduleRepo;
        this.lessonRepo = lessonRepo;
        this.stepRepo = stepRepo;
        this.statusRepo = statusRepo;
        this.moduleProgressRepo = moduleProgressRepo;
        this.lessonProgressRepo = lessonProgressRepo;
        this.stepProgressRepo = stepProgressRepo;
        this.moduleProgressMapper = moduleProgressMapper;
    }

//    This method updates the progress of a person
//    TODO: Return GetStepProgressResponse
//          Rewrite the method logic using Progress entities
//    public ResponseEntity<GetModuleProgressResponse> postStepProgress(
//            Long personId,
//            Long moduleId,
//            Long lessonId,
//            Long stepId) {
//        Optional<Person> optPerson = personRepo.findById(personId);
//        Optional<Module> optModule = moduleRepo.findById(moduleId);
//        Optional<Lesson> optLesson = lessonRepo.findById(lessonId);
//        Optional<Status> optStatus = statusRepo.findByName("Finished");
//
//        if (optPerson.isPresent()) {
//            Person person = optPerson.get();
//            if (optModule.isPresent()) {
//                if (optLesson.isPresent()) {
//                    Lesson oldLesson = optLesson.get();
//                    Step newStep = new Step(stepId);
//                    oldLesson.addStep(newStep);
//                    lessonRepo.save(oldLesson);
//                    return
//                }
//                Lesson newLesson = new Lesson(lessonId);
//                Step newStep = new Step(stepId);
//                newLesson.addStep(newStep);
//                lessonRepo.save(newLesson);
//                return
//            }
//            Module newModule = new Module(moduleId);
//            Lesson newLesson = new Lesson(lessonId);
//            Step newStep = new Step(stepId);
//            newLesson.addStep(newStep);
//            newModule.addLesson(newLesson);
//            moduleRepo.save(newModule);
//
//            ModuleProgress newModuleProgress = new ModuleProgress(person, newModule);
//            newModuleProgress = moduleProgressRepo.save(newModuleProgress);
//            LessonProgress newLessonProgress = new LessonProgress(person, newLesson);
//            newLessonProgress = lessonProgressRepo.save(newLessonProgress);
//
//            return
//        }
//        return
//    }

    public ResponseEntity<GetModuleProgressResponse> postStepProgress(
            Long personId,
            Long moduleId,
            Long lessonId,
            Long stepId) {
        Optional<Person> optPerson = personRepo.findById(personId);
        Optional<Module> optModule = moduleRepo.findById(moduleId);
        Optional<Lesson> optLesson = lessonRepo.findById(lessonId);

        if (optPerson.isPresent()) {

//            Check if it's an old Module
            if (optModule.isPresent()) {
                Module oldModule = optModule.get();

//                Check if it's an old Lesson
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

//            Check if the Person already has Progress for this Module
            Optional<ModuleProgress> optModuleProgress =
                    moduleProgressRepo.findById(new ModuleProgressId(personId, moduleId));
            if (optModuleProgress.isPresent()) {
                ModuleProgress moduleProgress = optModuleProgress.get();

//            Check if the Person already has Progress for this Lesson
                Optional<LessonProgress> optLessonProgress =
                        lessonProgressRepo.findById(new LessonProgressId(personId, lessonId));
                if (optLessonProgress.isPresent()) {
                    stepProgressRepo.save(new StepProgress(person, step, status));
                    GetModuleProgressResponse response =
                            moduleProgressMapper.toDto(moduleProgress);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                lessonProgressRepo.save(new LessonProgress(person, lesson));
                stepProgressRepo.save(new StepProgress(person, step, status));
                GetModuleProgressResponse response =
                        moduleProgressMapper.toDto(moduleProgress);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ModuleProgress newModuleProgress =
                    moduleProgressRepo.save(new ModuleProgress(person, module));
            lessonProgressRepo.save(new LessonProgress(person, lesson));
            stepProgressRepo.save(new StepProgress(person, step, status));
            GetModuleProgressResponse response =
                    moduleProgressMapper.toDto(newModuleProgress);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    TODO: Update the docs
    /**
     * Creates step progress entries for all steps for a given person, setting initial statuses.
     * All steps are initially marked with the status 'Blocked'.
     * Future development should handle marking steps from the first module as 'Available'.
     *
     * @param person The person for whom to create step progress entries.
     */
    @Override
    public void create(Person person) {
        Iterable<Step> steps = stepRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");
        if (blocked.isPresent()) {
            for (Step step : steps) {
                person.addStepProgress(new StepProgress(person, step, blocked.get()));
            }
        }
//        TODO: Mark the steps that are from the first module lessons as "Available"
    }

    /**
     * Updates the status of a given step progress to a new status.
     * This method is intended to modify the progress status of steps as they are completed or updated.
     *
     * @param progress The step progress whose status needs updating.
     * @param status The new status to be applied.
     */
    @Override
    public void updateStatus(Progress progress, Status status) {
//        TODO: Finish the implementation
    }
}
