package app.components.mappers;

import app.dtos.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper {

    private final StepMapper stepMapper;

    public LessonMapper(StepMapper stepMapper) {
        this.stepMapper = stepMapper;
    }

    public GetLessonResponse toLesson(GetLessonRequest lessonRequest) {
        long id = lessonRequest.data().id();
        GetLessonRequest.Attributes attributes = lessonRequest.data().attributes();
        String title = attributes.title();
        String description = attributes.description();
        String status = attributes.status().status();
        GetStepsRequest stepsRequest = attributes.steps();
        List<GetStepResponse> stepsResponse = stepMapper.toSteps(stepsRequest);
        return new GetLessonResponse(id, title, description, status, stepsResponse);
    }

    public List<GetLessonResponse> toLessons(GetLessonsRequest lessonsRequest) {
        List<GetLessonResponse> lessonsResponse = new ArrayList<>();
        for (GetLessonsRequest.Data data : lessonsRequest.data()) {
            long id = data.id();
            GetLessonsRequest.Attributes attributes = data.attributes();
            String title = attributes.title();
            String description = attributes.description();
            String status = attributes.status().status();
            lessonsResponse.add(new GetLessonResponse(id, title, description, status, null));
        }
        return lessonsResponse;
    }
}
