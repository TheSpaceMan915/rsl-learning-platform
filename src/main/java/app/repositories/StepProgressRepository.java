package app.repositories;

import app.domain.progress.StepProgress;
import app.domain.progress.ids.StepProgressId;

import org.springframework.data.repository.CrudRepository;

public interface StepProgressRepository extends CrudRepository<StepProgress, StepProgressId> {

//    Iterable<PersonStepProgress> findAllByPerson(Person person);
}