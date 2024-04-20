package app.dtos;

import java.util.List;

public record GetLessonResponse(
        long id,
        String title,
        String description,
        String status,
        List<GetStepResponse> steps
) {
}
