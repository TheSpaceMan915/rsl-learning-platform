package app.repositories;

import app.entities.progress.PersonStepProgress;
import app.entities.progress.ids.PersonStepProgressId;

import org.springframework.data.repository.CrudRepository;

public interface PersonStepProgressRepository extends CrudRepository<PersonStepProgress, PersonStepProgressId> {

//    Iterable<PersonStepProgress> findAllByPerson(Person person);
}