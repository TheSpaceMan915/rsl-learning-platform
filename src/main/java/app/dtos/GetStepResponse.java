package app.dtos;

public record GetStepResponse(
        long id,
        String title,
        String description,
        String status,
        String type,
        String content
) {
}
