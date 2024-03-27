package ru.otus.mar.booklibrary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Автор")
public class AuthorDto {

    private UUID id;

    @Schema(description = "Наименование")
    private String name;

    public AuthorDto(String name) {
        this(null, name);
    }
}