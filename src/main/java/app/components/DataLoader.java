package app.components;

import app.entities.platform.*;
import app.entities.platform.Module;
import app.entities.progress.PersonLessonProgress;
import app.entities.progress.PersonModuleProgress;
import app.entities.progress.PersonStepProgress;
import app.repositories.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class DataLoader implements CommandLineRunner {

    private final PersonRepository personRepo;
    private final StatusRepository statusRepo;
    private final ModuleRepository moduleRepo;
    private final PersonModuleProgressRepository moduleProgressRepo;
    private final PersonLessonProgressRepository lessonProgressRepo;
    private final PersonStepProgressRepository stepProgressRepo;

    public DataLoader(PersonRepository personRepo,
                      StatusRepository statusRepo,
                      ModuleRepository moduleRepo,
                      PersonModuleProgressRepository moduleProgressRepo,
                      PersonLessonProgressRepository lessonProgressRepo,
                      PersonStepProgressRepository stepProgressRepo) {
        this.personRepo = personRepo;
        this.statusRepo = statusRepo;
        this.moduleRepo = moduleRepo;
        this.moduleProgressRepo = moduleProgressRepo;
        this.lessonProgressRepo = lessonProgressRepo;
        this.stepProgressRepo = stepProgressRepo;
    }

    @Override
    public void run(String... args) {
        Person person = new Person(
                "JKmfdv563gfv3",
                "225sv",
                Timestamp.valueOf("2023-04-22 14:32:00"));
        person = personRepo.save(person);

        Status blocked = statusRepo.save(new Status("Blocked"));
        Status available = statusRepo.save(new Status("Available"));
        Status completed = statusRepo.save(new Status("Completed"));

        Step step = new Step("5436");
        Lesson lesson = new Lesson("47324");
        Module module = new Module("35232");
        lesson.addStep(step);
        module.addLesson(lesson);
        module = moduleRepo.save(module);

        PersonModuleProgress moduleProgress =
                new PersonModuleProgress(person, module, completed);
        moduleProgress = moduleProgressRepo.save(moduleProgress);

        PersonLessonProgress lessonProgress =
                new PersonLessonProgress(person, lesson, completed);
        lessonProgress = lessonProgressRepo.save(lessonProgress);

        PersonStepProgress stepProgress =
                new PersonStepProgress(person, step, completed);
        stepProgress = stepProgressRepo.save(stepProgress);
    }
}
