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
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.service.BookService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Книги", description = "Книги")
public class BookController {

    private final BookService service;

    @GetMapping("/api/book")
    @Operation(summary = "Список")
    public List<BookDto> list() {
        return service.getAll();
    }

    @GetMapping("/api/book/{id}")
    @Operation(summary = "Получить")
    public BookDto get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping("/api/book")
    @Operation(summary = "Добавить")
    public BookDto create(@RequestBody BookDto book) {
        book.setId(null);
        return service.create(book);
    }

    @PutMapping("/api/book/{id}")
    @Operation(summary = "Изменить")
    public BookDto update(@PathVariable UUID id, @RequestBody BookDto book) {
        book.setId(id);
        return service.update(book);
    }

    @DeleteMapping("/api/book/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
