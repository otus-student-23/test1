package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.AuthorRepository;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    private static final Book BOOK = new Book("Book_1", new Author("Author_1"), new Genre("Genre_1"));

    private static final BookDto BOOK_DTO = new BookDto("Book_1", new AuthorDto("Author_1"), new GenreDto("Genre_1"));

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepo;

    @MockBean
    private BookCommentRepository bookCommentRepo;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepo;

    @MockBean
    private GenreService genreService;

    @Test
    void create() {
        when(authorService.create(BOOK_DTO.getAuthor())).thenReturn(BOOK_DTO.getAuthor());
        when(genreService.create(BOOK_DTO.getGenre())).thenReturn(BOOK_DTO.getGenre());
        when(bookRepo.save(any())).thenReturn(BOOK);
        assertEquals(BOOK_DTO, bookService.create(BOOK_DTO));
    }

    @Test
    void update() {
        when(authorService.create(BOOK_DTO.getAuthor())).thenReturn(BOOK_DTO.getAuthor());
        when(genreService.create(BOOK_DTO.getGenre())).thenReturn(BOOK_DTO.getGenre());
        when(bookRepo.save(any())).thenReturn(BOOK);
        assertEquals(BOOK_DTO, bookService.update(BOOK_DTO));
    }

    @Test
    void delete() {
        when(bookRepo.findByNameAndAuthor(BOOK.getName(), BOOK.getAuthor())).thenReturn(Optional.of(BOOK));
        when(authorRepo.findByName(BOOK.getAuthor().getName())).thenReturn(Optional.of(BOOK.getAuthor()));
        bookService.delete(BOOK_DTO.getId());
        verify(bookRepo, times(1)).deleteById(BOOK.getId());
    }

    @Test
    void getAll() {
        when(bookRepo.findAll()).thenReturn(List.of(BOOK));
        assertTrue(bookService.getAll().containsAll(List.of(BOOK_DTO)));
    }
}
