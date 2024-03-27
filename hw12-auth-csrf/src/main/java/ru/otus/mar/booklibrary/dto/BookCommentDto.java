package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Комментарий к книге")
public class BookCommentDto {

    private UUID id;

    @Schema(description = "Книга")
    private BookDto book;

    @Schema(description = "Комментарий")
    private String comment;

    public BookCommentDto(BookDto book, String comment) {
        this(null, book, comment);
    }
}
