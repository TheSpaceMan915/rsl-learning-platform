package app.dtos;

import app.dtos.shared.MetaData;
import app.dtos.shared.Status;

public record GetModuleRequest(
        Data data,
        MetaData meta
) {
    public record Data(
            long id,
            Attributes attributes
    ) {}

    public record Attributes(
            String title,
            String description,
            String createdAt,
            String updatedAt,
            String publishedAt,
            Status status,
            GetLessonsRequest lectures
    ) {}
}
