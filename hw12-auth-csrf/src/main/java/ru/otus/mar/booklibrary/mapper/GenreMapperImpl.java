package ru.otus.mar.booklibrary.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.model.Genre;

@Service
public class GenreMapperImpl implements GenreMapper {

    @Override
    public GenreDto toDto(Genre genre) {
        return genre == null ? null : new GenreDto(genre.getId(), genre.getName());
    }

    @Override
    public Genre toEntity(GenreDto genre) {
        return genre == null ? null : new Genre(genre.getId(), genre.getName());
    }
}
