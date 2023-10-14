package app.repositories;

import app.entities.progress.PersonModuleProgress;
import app.entities.progress.ids.PersonModuleProgressId;

import org.springframework.data.repository.CrudRepository;

public interface PersonModuleProgressRepository extends CrudRepository<PersonModuleProgress, PersonModuleProgressId> {

//    Iterable<PersonModuleProgress> findAllByPerson(Person person);
}