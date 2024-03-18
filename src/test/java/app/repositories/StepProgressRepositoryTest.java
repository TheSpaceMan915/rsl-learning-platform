package app.repositories;

import app.domain.Person;
import app.domain.Status;
import app.domain.Step;
import app.domain.progress.StepProgress;
import app.domain.progress.ids.StepProgressId;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class StepProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private StepRepository stepRepo;

    @Autowired
    private StepProgressRepository progressRepo;

    private StepProgress createProgress() {
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

        StepProgress created =
                new StepProgress(person, step, available);
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
        Iterable<StepProgress> progresses =
                person.getStepProgresses();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING STEP PROGRESS");
        StepProgress saved = progressRepo.save(createProgress());
        log.info("saved: {}", saved);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING STEP PROGRESS");
        StepProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        StepProgress deleted = progressRepo
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
        StepProgress progress = progressRepo
                .findById(new StepProgressId(1L, 1L, 3L))
                .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeStepProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<StepProgress> progresses =
                person.getStepProgresses();
        assertThat(progresses).isEmpty();
    }
}
