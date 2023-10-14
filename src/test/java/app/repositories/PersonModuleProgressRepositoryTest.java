package app.repositories;

import app.entities.platform.Module;
import app.entities.platform.Person;
import app.entities.progress.PersonModuleProgress;
import app.entities.platform.Status;
import app.entities.progress.ids.PersonModuleProgressId;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class PersonModuleProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private ModuleRepository moduleRepo;

    @Autowired
    private PersonModuleProgressRepository progressRepo;

    private PersonModuleProgress createProgress() {
        log.info("----------------------- CREATE -------------------------");
        log.info("CREATING MODULE PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        Status available = statusRepo.findById(2L).orElseThrow();
        Module module = moduleRepo.findById(1L).orElseThrow();
        assertThat(person.getId()).isNotNull();
        assertThat(module.getId()).isNotNull();
        assertThat(available.getId()).isNotNull();
        log.info("person: {}", person);
        log.info("available: {}", available);
        log.info("module: {}", module);

        PersonModuleProgress created =
                new PersonModuleProgress(person, module, available);
        log.info("created: {}", created);
        log.info("----------------------- END CREATE -------------------------");
        return created;
    }

    @Test
    @Transactional
    public void findAllProgressesByPerson() {
        log.info("----------------------- FIND -------------------------");
        log.info("FINDING MODULE PROGRESSES BY PERSON");
        Person person = personRepo.findById(1L).orElseThrow();
        Iterable<PersonModuleProgress> progresses =
                person.getModuleProgresses();
        assertThat(person.getId()).isNotNull();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING MODULE PROGRESS");
        PersonModuleProgress saved = progressRepo.save(createProgress());
        log.info("saved: {}", saved);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING MODULE PROGRESS");
        PersonModuleProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        PersonModuleProgress deleted = progressRepo
                        .findById(created.getId())
                        .orElse(null);
        log.info("deleted: {}", deleted);
        assertThat(deleted).isNull();
    }

    @Test
    @Transactional
    public void deletePersonProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING PERSON MODULE PROGRESS");
        Person person = personRepo.findById(1L).orElseThrow();
        PersonModuleProgress progress = progressRepo
                        .findById(new PersonModuleProgressId(1L, 1L, 3L))
                        .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeModuleProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<PersonModuleProgress> progresses =
                person.getModuleProgresses();
        assertThat(progresses).isEmpty();
    }
}
