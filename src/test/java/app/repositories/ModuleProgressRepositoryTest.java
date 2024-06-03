package app.repositories;

import app.domain.Module;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.ModuleProgress;
import app.domain.progress.ids.ModuleProgressId;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ModuleProgressRepositoryTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private StatusRepository statusRepo;

    @Autowired
    private ModuleRepository moduleRepo;

    @Autowired
    private ModuleProgressRepository progressRepo;

    private ModuleProgress createProgress() {
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

        ModuleProgress created =
                new ModuleProgress(person, module);
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
        Iterable<ModuleProgress> progresses =
                person.getModuleProgresses();
        assertThat(person.getId()).isNotNull();
        assertThat(progresses).isNotEmpty();
    }

    @Test
    public void saveProgress() {
        log.info("----------------------- SAVE -------------------------");
        log.info("SAVING MODULE PROGRESS");
        ModuleProgress saved = progressRepo.save(createProgress());
        log.info("saved: {}", saved);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void deleteProgress() {
        log.info("----------------------- DELETE -------------------------");
        log.info("DELETING MODULE PROGRESS");
        ModuleProgress created = createProgress();
        progressRepo.deleteById(created.getId());
        ModuleProgress deleted = progressRepo
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
        ModuleProgress progress = progressRepo
                        .findById(new ModuleProgressId(1L, 1L))
                        .orElseThrow();
        log.info("person: {}", person);
        log.info("progress: {}", progress);
        assertThat(person.getId()).isNotNull();
        assertThat(progress.getId()).isNotNull();

        person.removeModuleProgress(progress);
        person = personRepo.save(person);
        person = personRepo.findById(1L).orElseThrow();
        Iterable<ModuleProgress> progresses =
                person.getModuleProgresses();
        assertThat(progresses).isEmpty();
    }
}
