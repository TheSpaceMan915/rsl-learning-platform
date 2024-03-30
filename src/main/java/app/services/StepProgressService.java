package app.services;

import app.domain.Person;
import app.domain.Status;
import app.domain.Step;
import app.domain.progress.StepProgress;
import app.repositories.StatusRepository;
import app.repositories.StepRepository;
import app.services.interfaces.Progressive;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StepProgressService implements Progressive {

    private StepRepository stepRepo;
    private StatusRepository statusRepo;

    public StepProgressService(StepRepository stepRepo,
                               StatusRepository statusRepo) {
        this.stepRepo = stepRepo;
        this.statusRepo = statusRepo;
    }

    @Override
    public void create(Person person) {
        Iterable<Step> steps = stepRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");

//        Create Step Progresses
        if (blocked.isPresent()) {
            for (Step step : steps) {
                person.addStepProgress(new StepProgress(person, step, blocked.get()));
            }
        }
        //        TODO:
        //         Mark the steps that are from the first module lessons as "Available"
    }
}
