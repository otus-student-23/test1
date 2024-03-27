package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.AuthorDto;

import java.util.Optional;
import java.util.UUID;

public interface AuthorService extends AbstractCrudService<AuthorDto, UUID> {

    Optional<AuthorDto> getByName(String name);
}
