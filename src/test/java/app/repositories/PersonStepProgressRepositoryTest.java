package app.repositories;

import app.entities.platform.Person;
import app.entities.platform.Status;
import app.entities.platform.Step;
import app.entities.progress.PersonStepProgress;
import app.entities.progress.ids.PersonStepProgressId;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class PersonStepProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private StepRepository stepRepo;

    @Autowired
    private PersonStepProgressRepository progressRepo;

    private PersonStepProgress createProgress() {
        log.info("----------------------- CREATE -------------------------");
        log.info("CREATING STEP PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        Status available = statusRepo.findById(2L).orElseThrow();
        Step step = stepRepo.findById(1L).orElseThrow();
        assertThat(person.getId()).isNotNull();
        assertThat(step.getId()).isNotNull();
        assertThat(available.getId()).isNotNull();
        log.info("person: {}", person);
        log.info("available: {}", available);
        log.info("step: {}", step);

        PersonStepProgress created =
                new PersonStepProgress(person, step, available);
        log.info("created: {}", created);
        log.info("----------------------- END CREATE -------------------------");
        return created;
    }

    @Test
    @Transactional
    public void findAllProgressesByPerson() {
        log.info("----------------------- FIND -------------------------");
        log.info("FINDING STEP PROGRESSES BY PERSON");
        Person person = personRepo.findById(1L).orElseThrow();
        assertThat(person.getId()).isNotNull();
        Iterable<PersonStepProgress> progresses =
                person.getStepProgresses();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING STEP PROGRESS");
        PersonStepProgress saved = progressRepo.save(createProgress());
        log.info("saved: {}", saved);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING STEP PROGRESS");
        PersonStepProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        PersonStepProgress deleted = progressRepo
                .findById(created.getId())
                .orElse(null);
        log.info("deleted: {}", deleted);
        assertThat(deleted).isNull();
    }

    @Test
    @Transactional
    public void deletePersonProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING PERSON STEP PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        PersonStepProgress progress = progressRepo
                .findById(new PersonStepProgressId(1L, 1L, 3L))
                .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeStepProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<PersonStepProgress> progresses =
                person.getStepProgresses();
        assertThat(progresses).isEmpty();
    }
}
