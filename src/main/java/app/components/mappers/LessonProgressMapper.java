package app.components.mappers;

import app.domain.Lesson;
import app.dtos.GetLessonProgressResponse;

import org.springframework.stereotype.Component;

@Component
public class LessonProgressMapper {

    public GetLessonProgressResponse toDto(Lesson lesson) {
        return new GetLessonProgressResponse(lesson.getId());
    }
}
