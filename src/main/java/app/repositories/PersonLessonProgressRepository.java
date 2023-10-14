package app.repositories;

import app.entities.progress.PersonLessonProgress;
import app.entities.progress.ids.PersonLessonProgressId;

import org.springframework.data.repository.CrudRepository;

public interface PersonLessonProgressRepository extends CrudRepository<PersonLessonProgress, PersonLessonProgressId> {

//    Iterable<PersonLessonProgress> findAllByPerson(Person person);
}