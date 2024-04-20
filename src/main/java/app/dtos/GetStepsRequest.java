package app.dtos;

import java.util.List;

import app.dtos.shared.MetaData;
import app.dtos.shared.Status;

public record GetStepsRequest(
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
            List<Content> content,
            Type type
    ) {}

    public record Content(
            long id,
            String __component,
            String text
    ) {}

    public record Type(
            long id,
            String type
    ) {}
}
