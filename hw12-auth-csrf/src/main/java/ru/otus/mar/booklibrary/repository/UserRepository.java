package ru.otus.mar.booklibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.mar.booklibrary.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}