package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.exception.NotFoundException;
import ru.otus.mar.booklibrary.mapper.BookCommentMapper;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;
import ru.otus.mar.booklibrary.repository.BookRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository repo;

    private final BookCommentMapper mapper;

    private final BookRepository bookRepo;

    @Override
    public BookCommentDto get(UUID id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public BookCommentDto create(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.toEntity(comment)));
    }

    @Override
    public BookCommentDto update(BookCommentDto comment) {
        return mapper.toDto(repo.save(mapper.toEntity(comment)));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public List<BookCommentDto> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCommentDto> getByBookId(UUID bookId) {
        return bookRepo.findById(bookId).get().getComments().stream().map(mapper::toDto).toList();
    }
}
