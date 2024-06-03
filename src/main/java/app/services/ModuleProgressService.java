package app.services;

import app.components.mappers.ModuleProgressMapper;
import app.domain.Lesson;
import app.domain.Module;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.Progress;
import app.dtos.GetModuleProgressResponse;
import app.repositories.LessonProgressRepository;
import app.repositories.ModuleRepository;
import app.repositories.PersonRepository;
import app.repositories.StatusRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing module progress within the educational platform.
 * This class handles the creation and updating of module progress entries for individuals.
 *
 * <p>Implements the {@link Progressive} interface, which defines standard operations for managing progress entries.
 *
 * @author  Nikita Kolychev
 */
@Service
public class ModuleProgressService implements Progressive {

    private final ModuleProgressMapper moduleProgressMapper;
    private final LessonProgressRepository lessonProgressRepo;
    private final PersonRepository personRepo;
    private final ModuleRepository moduleRepo;
    private final StatusRepository statusRepo;

    /**
     * Constructs a ModuleProgressService with necessary repositories.
     *
     * @param moduleRepo Repository for accessing modules
     * @param statusRepo Repository for accessing statuses
     */
    public ModuleProgressService(ModuleProgressMapper moduleProgressMapper,
                                 LessonProgressRepository lessonProgressRepo,
                                 PersonRepository personRepo,
                                 ModuleRepository moduleRepo,
                                 StatusRepository statusRepo) {
        this.moduleProgressMapper = moduleProgressMapper;
        this.lessonProgressRepo = lessonProgressRepo;
        this.personRepo = personRepo;
        this.moduleRepo = moduleRepo;
        this.statusRepo = statusRepo;
    }

    @Transactional
    public ResponseEntity<List<GetModuleProgressResponse>> getAll(Long personId) {
        Optional<Person> optPerson = personRepo.findById(personId);
        if (optPerson.isPresent()) {
            List<GetModuleProgressResponse> moduleProgresses = optPerson
                    .get()
                    .getModuleProgresses()
                    .stream()
                    .map(moduleProgressMapper::toDto)
                    .toList();
            return new ResponseEntity<>(moduleProgresses, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @Transactional
//    public ResponseEntity<List<GetModuleProgressResponse>> getAll(Long personId) {
//        Optional<Person> optPerson = personRepo.findById(personId);
//        if (optPerson.isPresent()) {
//
////                Get the Person's ModuleProgresses
//            List<ModuleProgress> moduleProgresses = optPerson
//                    .get()
//                    .getModuleProgresses();
//            List<GetModuleProgressResponse> responses = new ArrayList<>();
//
//            for (ModuleProgress moduleProgress : moduleProgresses) {
//                Module module = moduleProgress.getModule();
//                List<LessonProgress> lessonProgresses = new ArrayList<>();
//
////                Get the Person's LessonProgresses
//                for (Lesson lesson : module.getLessons()) {
//                    lessonProgresses.add()
//                }
//            }
//            return new ResponseEntity<>(moduleProgresses, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//  TODO: Update the docs
    /**
     * Creates module progress entries for all modules for a given person, setting initial statuses.
     * All modules are initially marked with the status 'Blocked'.
     * Plans to make the first module 'Available'.
     *
     * @param person The person for whom to create module progress entries.
     */
    @Override
    public void create(Person person) {
        Iterable<Module> modules = moduleRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");
        if (blocked.isPresent()) {
            for (Module module : modules) {
                person.addModuleProgress(new ModuleProgress(person, module));
            }
        }
//        TODO: Mark the first module as "Available"
    }

    /**
     * Updates the status of a given module progress to a new status.
     * This method is intended to modify the progress status of modules as they are completed or updated.
     *
     * @param progress The module progress whose status needs updating.
     * @param status The new status to be applied.
     */
    @Override
    public void updateStatus(Progress progress, Status status) {
//        TODO: Finish the implementation
    }
}
