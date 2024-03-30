package app.services;

import app.domain.Lesson;
import app.domain.Person;
import app.domain.Status;
import app.domain.progress.LessonProgress;
import app.repositories.LessonRepository;
import app.repositories.StatusRepository;
import app.services.interfaces.Progressive;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonProgressService implements Progressive {

    private LessonRepository lessonRepo;
    private StatusRepository statusRepo;

    public LessonProgressService(LessonRepository lessonRepo,
                                 StatusRepository statusRepo) {
        this.lessonRepo = lessonRepo;
        this.statusRepo = statusRepo;
    }

    @Override
    public void create(Person person) {
        Iterable<Lesson> lessons = lessonRepo.findAll();
        Optional<Status> blocked = statusRepo.findByName("Blocked");

//        Create Lesson Progresses
        if (blocked.isPresent()) {
            for (Lesson lesson : lessons) {
                person.addLessonProgress(new LessonProgress(person, lesson, blocked.get()));
            }
        }
        //        TODO: Mark the lessons from the first module as "Available"
    }
}
