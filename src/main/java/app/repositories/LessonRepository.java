package app.repositories;

import app.domain.Lesson;

import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Long> {
}