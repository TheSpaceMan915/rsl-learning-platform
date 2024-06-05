package app.services;

import app.domain.*;
import app.domain.progress.StepProgress;
import app.repositories.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StepProgressService {

    private final StepProgressRepository stepProgressRepo;

    /**
     * Constructs a new StepProgressService with necessary repositories for managing steps and statuses.
     *
     * @param stepRepo Repository for accessing step data
     * @param statusRepo Repository for accessing status data
     */
    public StepProgressService(StepProgressRepository stepProgressRepo) {
        this.stepProgressRepo = stepProgressRepo;
    }

//    Finds studied Steps from the Lessons
    public Map<Lesson, List<StepProgress>> getAllStudied(
            List<Lesson> lessons,
            Person person) {
        Map<Lesson, List<StepProgress>> stepProgressMap = new HashMap<>();
        for (Lesson lesson : lessons) {
            List<StepProgress> stepProgresses = new ArrayList<>();
            for (Step step : lesson.getSteps()) {
                Optional<StepProgress> optStepProgress =
                        stepProgressRepo.findByIdPersonIdAndIdStepId(
                                person.getId(), step.getId());
                optStepProgress.ifPresent(stepProgresses::add);
            }
            if (!stepProgresses.isEmpty()) {
                stepProgressMap.put(lesson, stepProgresses);
            }
        }
        return stepProgressMap;
    }
}
