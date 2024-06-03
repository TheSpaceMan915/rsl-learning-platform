package app.services;

import app.domain.Lesson;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.LessonProgress;
import app.domain.progress.Progress;
import app.repositories.LessonRepository;
import app.repositories.StatusRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing lesson progress within the educational platform.
 * This class handles the creation of lesson progress entries for individuals, considering their current status.
 *
 * <p>Implements the {@link Progressive} interface, which defines standard operations for managing progress entries.
 *
 * @author  Nikita Kolychev
 */
@Service
public class LessonProgressService implements Progressive {

    private final LessonRepository lessonRepo;
    private final StatusRepository statusRepo;

    /**
     * Constructs a LessonProgressService with necessary repositories.
     *
     * @param lessonRepo Repository for accessing lessons
     * @param statusRepo Repository for accessing statuses
     */
    public LessonProgressService(LessonRepository lessonRepo,
                                 StatusRepository statusRepo) {
        this.lessonRepo = lessonRepo;
        this.statusRepo = statusRepo;
    }

//    TODO: Update the docs
    /**
     * Creates lesson progress entries for all lessons for a given person, setting initial statuses.
     * All lessons are initially marked with the status 'Blocked'.
     * Future development should handle marking lessons from the first module as 'Available'.
     *
     * @param person The person for whom to create lesson progress entries.
     */
    @Override
    public void create(Person person) {
        Iterable<Lesson> lessons = lessonRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");
        if (blocked.isPresent()) {
            for (Lesson lesson : lessons) {
                person.addLessonProgress(new LessonProgress(person, lesson));
            }
        }
        //  TODO: Mark the lessons from the first module as "Available"
    }

    /**
     * Updates the status of a given lesson progress to a new status.
     * This method is intended to modify the progress status of lessons as they are completed or updated.
     *
     * @param progress The lesson progress whose status needs updating.
     * @param status The new status to be applied.
     */
    @Override
    public void updateStatus(Progress progress, Status status) {
//        TODO: Finish the implementation
    }
}
