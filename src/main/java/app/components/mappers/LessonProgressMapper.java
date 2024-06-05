package app.components.mappers;

import app.domain.Lesson;
import app.domain.progress.StepProgress;
import app.dtos.GetLessonProgressResponse;
import app.dtos.GetStepProgressResponse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonProgressMapper {

    private final StepProgressMapper stepProgressMapper;

    public LessonProgressMapper(StepProgressMapper stepProgressMapper) {
        this.stepProgressMapper = stepProgressMapper;
    }

    public GetLessonProgressResponse toDto(Lesson lesson, List<StepProgress> stepProgresses) {
        Long id = lesson.getId();
        List<GetStepProgressResponse> stepResponses = new ArrayList<>();
        for (StepProgress stepProgress : stepProgresses) {
            GetStepProgressResponse response =
                    stepProgressMapper.toDto(stepProgress);
            stepResponses.add(response);
        }
        return new GetLessonProgressResponse(id, stepResponses);
    }
}
