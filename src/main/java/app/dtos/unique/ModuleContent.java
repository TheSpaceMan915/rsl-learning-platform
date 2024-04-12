package app.dtos.unique;

import app.dtos.common.MetaData;
import app.dtos.common.Status;

import java.util.List;

public record ModuleContent(
        List<Data> data,
        MetaData meta
) {
    private record Data(
            long id,
            Attributes attributes
    ) {}

    private record Attributes(
            String title,
            String description,
            String createdAt,
            String updatedAt,
            String publishedAt,
            Status status,
            LessonContent lectures
    ) {}
}
