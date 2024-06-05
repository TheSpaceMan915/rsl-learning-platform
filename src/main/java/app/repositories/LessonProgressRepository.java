package app.repositories;

import app.domain.Lesson;
import app.domain.Person;
import app.domain.progress.LessonProgress;
import app.domain.progress.ids.LessonProgressId;

import org.springframework.data.repository.CrudRepository;

public interface LessonProgressRepository extends CrudRepository<LessonProgress, LessonProgressId> {

//    Iterable<LessonProgress> findAllByPersonAndLesson(Person person, Lesson lesson);
}