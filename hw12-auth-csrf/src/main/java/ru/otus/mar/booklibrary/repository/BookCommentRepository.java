package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.booklibrary.model.BookComment;

import java.util.UUID;

public interface BookCommentRepository extends JpaRepository<BookComment, UUID> {
}
