package app.repositories;

import app.domain.progress.StepProgress;
import app.domain.progress.ids.StepProgressId;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StepProgressRepository extends CrudRepository<StepProgress, StepProgressId> {

    Optional<StepProgress> findByIdPersonIdAndIdStepId(Long personId, Long stepId);

//    Iterable<PersonStepProgress> findAllByPerson(Person person);
}