package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Книга")
public class BookDto {

    private UUID id;

    @Schema(description = "Наименование")
    private String name;

    @Schema(description = "Автор")
    private AuthorDto author;

    @Schema(description = "Жанр")
    private GenreDto genre;

    public BookDto(String name, AuthorDto author, GenreDto genre) {
        this(null, name, author, genre);
    }
}