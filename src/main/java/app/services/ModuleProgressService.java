package app.services;

import app.domain.Module;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.ModuleProgress;
import app.domain.progress.Progress;
import app.repositories.ModuleRepository;
import app.repositories.StatusRepository;

import org.springframework.stereotype.Service;

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

    private final ModuleRepository moduleRepo;
    private final StatusRepository statusRepo;

    /**
     * Constructs a ModuleProgressService with necessary repositories.
     *
     * @param moduleRepo Repository for accessing modules
     * @param statusRepo Repository for accessing statuses
     */
    public ModuleProgressService(ModuleRepository moduleRepo,
                                 StatusRepository statusRepo) {
        this.moduleRepo = moduleRepo;
        this.statusRepo = statusRepo;
    }

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
                person.addModuleProgress(new ModuleProgress(person, module, blocked.get()));
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
