package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.BookFilterDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService extends AbstractCrudService<BookDto, UUID> {

    Optional<BookDto> getByNameAndAuthor(String name, AuthorDto author);

    List<BookDto> getByFilter(BookFilterDto filter);
}
