package app.services;

import app.domain.Person;
import app.domain.Status;
import app.domain.progress.Progress;

/**
 * Defines standard operations for managing progress entries across various entities
 * within the application. This interface ensures that all services handling progress
 * have a consistent set of functionalities.
 *
 * <p>Implementing services will manage different aspects of user progress, such as
 * initialising progress entries and updating their statuses as users advance through the platform.</p>
 *
 * @author  Nikita Kolychev
 */
public interface Progressive {

    /**
     * Initializes progress entries for a specific user. This method is typically implemented
     * to set up initial progress states when a new user is registered or when a new progress
     * trackable activity is started.
     *
     * @param person The user for whom to initialize progress entries.
     */
    void create(Person person);

    /**
     * Updates the status of a given progress entry to a new status. This method is intended
     * to modify the progress status of entries as they are completed or updated, reflecting
     * the user's advancement or changes in their activity status.
     *
     * @param progress The progress entry whose status needs updating.
     * @param status The new status to be applied, reflecting the current state of the progress.
     */
    void updateStatus(Progress progress, Status status);
}