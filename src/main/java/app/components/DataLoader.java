package app.components;

import app.domain.*;
import app.domain.Module;
import app.domain.progress.LessonProgress;
import app.domain.progress.ModuleProgress;
import app.domain.progress.StepProgress;
import app.repositories.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PersonRepository personRepo;
    private final StatusRepository statusRepo;
    private final ModuleRepository moduleRepo;
    private final ModuleProgressRepository moduleProgressRepo;
    private final LessonProgressRepository lessonProgressRepo;
    private final StepProgressRepository stepProgressRepo;

    public DataLoader(PersonRepository personRepo,
                      StatusRepository statusRepo,
                      ModuleRepository moduleRepo,
                      ModuleProgressRepository moduleProgressRepo,
                      LessonProgressRepository lessonProgressRepo,
                      StepProgressRepository stepProgressRepo) {
        this.personRepo = personRepo;
        this.statusRepo = statusRepo;
        this.moduleRepo = moduleRepo;
        this.moduleProgressRepo = moduleProgressRepo;
        this.lessonProgressRepo = lessonProgressRepo;
        this.stepProgressRepo = stepProgressRepo;
    }

    @Override
    public void run(String... args) {
        Person person = new Person();
        person.setId(1L);
        person = personRepo.save(person);

        Status available = statusRepo.save(new Status("Available"));
        Status finished = statusRepo.save(new Status("Finished"));
//        Status blocked = statusRepo.save(new Status("Blocked"));
//        Status inProgress = statusRepo.save(new Status("In progress"));

        Module module1 = new Module(3522L);
        Module module2 = new Module(3532L);
        Lesson lesson1 = new Lesson(7324L);
        Lesson lesson2 = new Lesson(5324L);
//        Lesson lesson3 = new Lesson(5325L);
        Step step1 = new Step(5436L);
        Step step2 = new Step(7365L);
        Step step3 = new Step(7366L);
//        Step step4 = new Step(3356L);

//        lesson3.addStep(step4);
//        module1.addLesson(lesson3);
        module1 = saveModule(module1, lesson1, step1);
        lesson2.addStep(step3);
        module2 = saveModule(module2, lesson2, step2);

        ModuleProgress moduleProgress1 =
                new ModuleProgress(person, module1);
        moduleProgress1 = moduleProgressRepo.save(moduleProgress1);
        ModuleProgress moduleProgress2 =
                new ModuleProgress(person, module2);
        moduleProgress2 = moduleProgressRepo.save(moduleProgress2);

//        LessonProgress lessonProgress1 =
//                new LessonProgress(person, lesson1);
//        lessonProgress1 = lessonProgressRepo.save(lessonProgress1);
//        LessonProgress lessonProgress2 =
//                new LessonProgress(person, lesson2);
//        lessonProgress2 = lessonProgressRepo.save(lessonProgress2);

//        StepProgress stepProgress1 =
//                new StepProgress(person, step1, available);
//        stepProgress1 = stepProgressRepo.save(stepProgress1);
//        StepProgress stepProgress2 =
//                new StepProgress(person, step2, available);
//        stepProgress2 = stepProgressRepo.save(stepProgress2);
//        StepProgress stepProgress3 =
//                new StepProgress(person, step3, available);
//        stepProgress3 = stepProgressRepo.save(stepProgress3);
    }

    private Module saveModule(Module module, Lesson lesson, Step step) {
        lesson.addStep(step);
        module.addLesson(lesson);
        return moduleRepo.save(module);
    }
}
