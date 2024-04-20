package app.dtos;

import java.util.List;

public record GetModuleResponse(
       long id,
       String title,
       String description,
       String status,
       List<GetLessonResponse> lectures
) {
}
