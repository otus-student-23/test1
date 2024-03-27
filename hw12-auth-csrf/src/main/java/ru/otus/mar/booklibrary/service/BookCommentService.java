package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.BookCommentDto;

import java.util.List;
import java.util.UUID;

public interface BookCommentService extends AbstractCrudService<BookCommentDto, UUID> {

    List<BookCommentDto> getByBookId(UUID bookId);
}
