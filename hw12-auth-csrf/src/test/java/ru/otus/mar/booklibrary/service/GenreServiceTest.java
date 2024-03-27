package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Genre;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GenreServiceTest {

    private static final GenreDto GENRE_DTO = new GenreDto("Genre_1");

    private static final Genre GENRE = new Genre("Genre_1");

    @Autowired
    private GenreService service;

    @MockBean
    private GenreRepository repo;

    @Test
    void create() {
        when(repo.findByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        assertEquals(GENRE_DTO, service.create(GENRE_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(GENRE);
        assertEquals(GENRE_DTO, service.update(GENRE_DTO));
    }

    @Test
    void delete() {
        when(repo.findByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        service.delete(GENRE_DTO.getId());
        verify(repo, times(1)).deleteById(GENRE.getId());
    }

    @Test
    void getAll() {
        when(repo.findAll()).thenReturn(List.of(GENRE));
        assertTrue(service.getAll().containsAll(List.of(GENRE_DTO)));
    }

    @Test
    void getByName() {
        when(repo.findByName(GENRE.getName())).thenReturn(Optional.of(GENRE));
        assertEquals(GENRE_DTO, service.getByName(GENRE.getName()).get());
    }
}
