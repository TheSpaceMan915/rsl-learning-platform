package app.repositories;

import app.domain.progress.ids.LessonProgressId;
import app.domain.Lesson;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.LessonProgress;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class LessonProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private LessonProgressRepository progressRepo;

    private LessonProgress createProgress() {
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

        LessonProgress created =
                new LessonProgress(person, lesson);
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
        Iterable<LessonProgress> progresses =
                person.getLessonProgresses();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING LESSON PROGRESS");
        LessonProgress saved = progressRepo.save(createProgress());
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING LESSON PROGRESS");
        LessonProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        LessonProgress deleted = progressRepo
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
        LessonProgress progress = progressRepo
                .findById(new LessonProgressId(1L, 1L))
                .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeLessonProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<LessonProgress> progresses =
                person.getLessonProgresses();
        assertThat(progresses).isEmpty();
    }
}