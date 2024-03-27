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
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.service.BookCommentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Комментарии к книге", description = "Комментарии к книге")
public class BookCommentController {

    private final BookCommentService service;

    @GetMapping("/api/book/{bookId}/comment")
    @Operation(summary = "Список")
    public List<BookCommentDto> list(@PathVariable UUID bookId) {
        return service.getByBookId(bookId);
    }

    @GetMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Получить")
    public BookCommentDto get(@PathVariable UUID bookId, @PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping("/api/book/{bookId}/comment")
    @Operation(summary = "Добавить")
    public BookCommentDto create(@PathVariable UUID bookId, @RequestBody BookCommentDto comment) {
        comment.setId(null);
        BookDto book = new BookDto();
        book.setId(bookId);
        comment.setBook(book);
        return service.create(comment);
    }

    @PutMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Изменить")
    public BookCommentDto update(@PathVariable UUID id, @RequestBody BookCommentDto comment) {
        comment.setId(id);
        return service.update(comment);
    }

    @DeleteMapping("/api/book/{bookId}/comment/{id}")
    @Operation(summary = "Удалить")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
