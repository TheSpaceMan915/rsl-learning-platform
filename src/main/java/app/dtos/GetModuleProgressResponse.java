package app.dtos;

import java.util.List;

public record GetModuleProgressResponse(
        Long id,
        List<GetLessonProgressResponse> lessonProgresses
) {
}
