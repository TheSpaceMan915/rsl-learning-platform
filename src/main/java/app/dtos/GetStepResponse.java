package app.dtos;

import java.util.List;

public record GetStepResponse(
        long id,
        String title,
        String description,
        String status,
        String type,
        List<Object> content
) {
}
