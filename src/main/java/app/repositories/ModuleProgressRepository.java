package app.repositories;

import app.domain.progress.ModuleProgress;
import app.domain.progress.ids.ModuleProgressId;

import org.springframework.data.repository.CrudRepository;

public interface ModuleProgressRepository extends CrudRepository<ModuleProgress, ModuleProgressId> {

//    Iterable<PersonModuleProgress> findAllByPerson(Person person);
}