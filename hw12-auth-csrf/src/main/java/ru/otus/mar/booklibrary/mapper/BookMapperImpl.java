package ru.otus.mar.booklibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Book;

@RequiredArgsConstructor
@Service
public class BookMapperImpl implements BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre())
        );
    }

    @Override
    public Book toEntity(BookDto book) {
        return new Book(
                book.getId(),
                book.getName(),
                authorMapper.toEntity(book.getAuthor()),
                genreMapper.toEntity(book.getGenre()),
                null
        );
    }
}
