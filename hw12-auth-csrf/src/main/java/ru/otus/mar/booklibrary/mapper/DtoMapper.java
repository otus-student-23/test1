package ru.otus.mar.booklibrary.mapper;

public interface DtoMapper<M, D> {

    D toDto(M model);

    M toEntity(D dto);
}
