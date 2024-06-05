package app.dtos;

import java.util.List;

public record GetLessonProgressResponse(
        Long id,
        List<GetStepProgressResponse> stepProgresses
) {
}
