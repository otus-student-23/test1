package org.example.service;

import org.example.dto.AuthorDto;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface AuthorSender {

    CompletableFuture<SendResult<String, AuthorDto>> send(AuthorDto message);

    void send(AuthorDto message, Consumer<SendResult<String, AuthorDto>> callback);
}
