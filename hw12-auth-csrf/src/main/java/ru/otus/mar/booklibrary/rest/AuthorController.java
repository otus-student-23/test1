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
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.service.AuthorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Авторы", description = "Авторы")
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/api/author")
    @Operation(summary = "Список")
    public List<AuthorDto> list() {
        return service.getAll();
    }

    @GetMapping("/api/author/{id}")
    @Operation(summary = "Получить")
    public AuthorDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping("/api/author")
    @Operation(summary = "Добавить")
    public AuthorDto create(@RequestBody AuthorDto author) {
        author.setId(null);
        return service.create(author);
    }

    @PutMapping("/api/author/{id}")
    @Operation(summary = "Изменить")
    public AuthorDto update(@PathVariable UUID id, @RequestBody AuthorDto author) {
        author.setId(id);
        return service.update(author);
    }

    @DeleteMapping("/api/author/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
