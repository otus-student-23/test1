package ru.otus.mar.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.mar.booklibrary.dto.GenreDto;
import ru.otus.mar.booklibrary.exception.NotFoundException;
import ru.otus.mar.booklibrary.mapper.GenreMapper;
import ru.otus.mar.booklibrary.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repo;

    private final GenreMapper mapper;

    @Override
    public GenreDto get(UUID id) {
        return repo.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }

    @Override
    public GenreDto create(GenreDto genre) {
        return mapper.toDto(repo.findByName(genre.getName()).orElseGet(() -> repo.save(mapper.toEntity(genre))));
    }

    @Override
    public GenreDto update(GenreDto genre) {
        return mapper.toDto(repo.save(mapper.toEntity(genre)));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public List<GenreDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public Optional<GenreDto> getByName(String name) {
        return repo.findByName(name).map(mapper::toDto);
    }
}
