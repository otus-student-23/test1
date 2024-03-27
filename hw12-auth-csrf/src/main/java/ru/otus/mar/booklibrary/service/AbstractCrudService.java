package ru.otus.mar.booklibrary.service;

import java.util.List;

public interface AbstractCrudService<E, I> {

    E get(I id);

    E create(E entity);

    E update(E entity);

    void delete(I id);

    List<E> getAll();
}
