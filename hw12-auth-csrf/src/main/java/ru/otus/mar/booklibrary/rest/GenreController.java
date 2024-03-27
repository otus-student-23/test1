package ru.otus.mar.booklibrary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.service.GenreService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Жанры", description = "Жанры")
public class GenreController {
    
    private final GenreService service;

    @GetMapping("/api/genre")
    @Operation(summary = "Список")
    public List<GenreDto> list() {
        return service.getAll();
    }

    @GetMapping("/api/genre/{id}")
    @Operation(summary = "Получить")
    public GenreDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping("/api/genre")
    @Operation(summary = "Добавить")
    public GenreDto create(@RequestBody GenreDto genre) {
        genre.setId(null);
        return service.create(genre);
    }

    @PutMapping("/api/genre/{id}")
    @Operation(summary = "Изменить")
    public GenreDto update(@PathVariable UUID id, @RequestBody GenreDto genre) {
        genre.setId(id);
        return service.update(genre);
    }

    @DeleteMapping("/api/genre/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
