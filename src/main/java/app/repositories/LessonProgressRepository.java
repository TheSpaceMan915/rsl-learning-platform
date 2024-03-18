package app.repositories;

import app.domain.progress.LessonProgress;
import app.domain.progress.ids.LessonProgressId;

import org.springframework.data.repository.CrudRepository;

public interface LessonProgressRepository extends CrudRepository<LessonProgress, LessonProgressId> {

//    Iterable<PersonLessonProgress> findAllByPerson(Person person);
}