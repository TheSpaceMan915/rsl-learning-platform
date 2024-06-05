package app.repositories;

import app.domain.Module;
import app.domain.Person;
import app.domain.progress.ModuleProgress;
import app.domain.progress.ids.ModuleProgressId;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModuleProgressRepository extends CrudRepository<ModuleProgress, ModuleProgressId> {

//    ModuleProgress findByIdPersonId(Long personId);

//    Iterable<PersonModuleProgress> findAllByPerson(Person person);
}