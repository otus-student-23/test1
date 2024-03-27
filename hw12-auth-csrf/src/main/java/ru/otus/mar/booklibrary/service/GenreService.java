package ru.otus.mar.booklibrary.service;

import ru.otus.mar.booklibrary.dto.GenreDto;

import java.util.Optional;
import java.util.UUID;

public interface GenreService extends AbstractCrudService<GenreDto, UUID> {

    Optional<GenreDto> getByName(String name);
}
