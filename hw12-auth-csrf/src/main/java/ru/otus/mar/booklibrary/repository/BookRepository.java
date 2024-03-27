package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.otus.mar.booklibrary.model.Author;
import ru.otus.mar.booklibrary.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findByNameAndAuthor(String name, Author author);

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll(Specification<Book> filter);
}
