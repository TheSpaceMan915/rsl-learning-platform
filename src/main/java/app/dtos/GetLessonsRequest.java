package app.dtos;

import app.dtos.shared.MetaData;
import app.dtos.shared.Status;

import java.util.List;

public record GetLessonsRequest(
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
            GetStepsRequest steps
    ) {}
}
