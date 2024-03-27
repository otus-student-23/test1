package ru.otus.mar.booklibrary.mapper;

import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.dto.AuthorDto;

@Service
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto toDto(Author author) {
        return author == null ? null : new AuthorDto(author.getId(), author.getName());
    }

    @Override
    public Author toEntity(AuthorDto author) {
        return author == null ? null : new Author(author.getId(), author.getName());
    }
}
