package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dto.AuthorDto;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("Author_1");

    private static final Author AUTHOR = new Author("Author_1");

    @Autowired
    private AuthorService service;

    @MockBean
    private AuthorRepository repo;

    @Test
    void create() {
        when(repo.findByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        assertEquals(AUTHOR_DTO, service.create(AUTHOR_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(AUTHOR);
        assertEquals(AUTHOR_DTO, service.update(AUTHOR_DTO));
    }

    @Test
    void delete() {
        when(repo.findByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        service.delete(AUTHOR_DTO.getId());
        verify(repo, times(1)).deleteById(AUTHOR.getId());
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(AUTHOR));
        assertTrue(service.getAll().containsAll(List.of(AUTHOR_DTO)));
    }

    @Test
    void getByName() {
        when(repo.findByName(AUTHOR.getName())).thenReturn(Optional.of(AUTHOR));
        assertEquals(AUTHOR_DTO, service.getByName(AUTHOR.getName()).get());
    }
}
