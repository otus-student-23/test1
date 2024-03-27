package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.booklibrary.model.Genre;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findByName(String name);
}
