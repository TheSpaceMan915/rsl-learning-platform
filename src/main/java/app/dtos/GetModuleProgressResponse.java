package app.dtos;

import java.util.List;

public record GetModuleProgressResponse(
        Long id,
        String status,
        List<GetLessonProgressResponse> lessonProgresses
) {
}
