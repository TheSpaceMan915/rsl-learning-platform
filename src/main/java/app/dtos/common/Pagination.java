package app.dtos.common;

public record Pagination(
        int page,
        int pageSize,
        int pageCount,
        int total
) {
}
