package app.dtos;

import app.dtos.shared.MetaData;
import app.dtos.shared.Status;

import java.util.List;

public record GetStepRequest(
        Data data,
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
            List<Content> content,
            Type type
    ) {}

    private record Content(
            long id,
            String __component,
            String text
    ) {}

    private record Type(
            long id,
            String type
    ) {}
}
