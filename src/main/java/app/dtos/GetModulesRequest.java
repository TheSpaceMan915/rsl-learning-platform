package app.dtos;

import app.dtos.shared.MetaData;
import app.dtos.shared.Status;

import java.util.List;

public record GetModulesRequest(
        List<Data> data,
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
