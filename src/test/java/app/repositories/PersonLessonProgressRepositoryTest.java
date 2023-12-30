package app.repositories;

import app.entities.progress.ids.PersonLessonProgressId;
import app.entities.platform.Lesson;
import app.entities.platform.Person;
import app.entities.platform.Status;
import app.entities.progress.PersonLessonProgress;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class PersonLessonProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private PersonLessonProgressRepository progressRepo;

    private PersonLessonProgress createProgress() {
        log.info("----------------------- CREATE -------------------------");
        log.info("CREATING LESSON PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        Status available = statusRepo.findById(2L).orElseThrow();
        Lesson lesson = lessonRepo.findById(1L).orElseThrow();
        assertThat(person.getId()).isNotNull();
        assertThat(lesson.getId()).isNotNull();
        assertThat(available.getId()).isNotNull();
        log.info("person: {}", person);
        log.info("available: {}", available);
        log.info("lesson: {}", lesson);

        PersonLessonProgress created =
                new PersonLessonProgress(person, lesson, available);
        log.info("created: {}", created);
        log.info("----------------------- END CREATE -------------------------");
        return created;
    }

    @Test
    @Transactional
    public void findAllProgressesByPerson() {
        log.info("----------------------- FIND -------------------------");
        log.info("FINDING LESSON PROGRESSES BY PERSON");
        Person person = personRepo.findById(1L).orElseThrow();
        assertThat(person.getId()).isNotNull();
        Iterable<PersonLessonProgress> progresses =
                person.getLessonProgresses();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING LESSON PROGRESS");
        PersonLessonProgress saved = progressRepo.save(createProgress());
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING LESSON PROGRESS");
        PersonLessonProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        PersonLessonProgress deleted = progressRepo
                        .findById(created.getId())
                        .orElse(null);
        log.info("deleted: {}", deleted);
        assertThat(deleted).isNull();
    }

    @Test
    @Transactional
    public void deletePersonProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING PERSON LESSON PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        PersonLessonProgress progress = progressRepo
                .findById(new PersonLessonProgressId(1L, 1L, 3L))
                .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeLessonProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<PersonLessonProgress> progresses =
                person.getLessonProgresses();
        assertThat(progresses).isEmpty();
    }
}