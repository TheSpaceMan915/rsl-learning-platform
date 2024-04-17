package app.dtos.shared;

public record Pagination(
        int page,
        int pageSize,
        int pageCount,
        int total
) {
}
