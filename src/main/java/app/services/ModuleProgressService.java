package app.services;

import app.domain.Module;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.ModuleProgress;
import app.repositories.ModuleRepository;
import app.repositories.StatusRepository;
import app.services.interfaces.Progressive;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleProgressService implements Progressive {

    private ModuleRepository moduleRepo;
    private StatusRepository statusRepo;

    public ModuleProgressService(ModuleRepository moduleRepo,
                                 StatusRepository statusRepo) {
        this.moduleRepo = moduleRepo;
        this.statusRepo = statusRepo;
    }

    //        TODO:
    //         Think how to make the first Module, Lesson and its steps available.
//             Maybe we should number the Modules, Lessons and Steps
    @Override
    public void create(Person person) {
        Iterable<Module> modules = moduleRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");

//        Create Module Progresses
        if (blocked.isPresent()) {
            for (Module module : modules) {
                person.addModuleProgress(new ModuleProgress(person, module, blocked.get()));
            }
        }
//        TODO: Mark the Progress for the first Module as "Available"
    }

//    @Override
//    public void updateStatus(Progress progress, Status status) {
//
//    }
}
