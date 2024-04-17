package app.services;

import app.domain.Person;
import app.domain.Status;
import app.domain.Step;
import app.domain.progress.Progress;
import app.domain.progress.StepProgress;
import app.repositories.StatusRepository;
import app.repositories.StepRepository;

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

    private final StepRepository stepRepo;
    private final StatusRepository statusRepo;

    /**
     * Constructs a new StepProgressService with necessary repositories for managing steps and statuses.
     *
     * @param stepRepo Repository for accessing step data
     * @param statusRepo Repository for accessing status data
     */
    public StepProgressService(StepRepository stepRepo,
                               StatusRepository statusRepo) {
        this.stepRepo = stepRepo;
        this.statusRepo = statusRepo;
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
