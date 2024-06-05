package app.services;

import app.domain.Lesson;
import app.domain.Module;
import app.domain.Person;
import app.domain.progress.LessonProgress;
import app.domain.progress.ids.LessonProgressId;
import app.repositories.LessonProgressRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepo;

    public LessonProgressService(LessonProgressRepository lessonProgressRepo) {
        this.lessonProgressRepo = lessonProgressRepo;
    }

    //    Finds Lessons that have been studied
    public List<Lesson> getAllStudied(Module module, Person person) {
        List<Lesson> moduleLessons = module.getLessons();
        List<Lesson> studiedLessons = new ArrayList<>();
        for (Lesson lesson : moduleLessons) {
            Optional<LessonProgress> optLessonProgress =
                    lessonProgressRepo.findById(
                            new LessonProgressId(person.getId(), lesson.getId()));
            if (optLessonProgress.isPresent()) {
                studiedLessons.add(lesson);
            }
        }
        return studiedLessons;
    }
}
