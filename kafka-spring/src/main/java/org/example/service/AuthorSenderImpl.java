package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AuthorSenderImpl implements AuthorSender {

    private final KafkaTemplate<String, AuthorDto> authorTemplate;

    @Override
    public CompletableFuture<SendResult<String, AuthorDto>> send(AuthorDto author) {
        return authorTemplate.send("topic-spring-1", "0", author);
    }

    @Override
    public void send(AuthorDto author, Consumer<SendResult<String, AuthorDto>> callback) {
        try {
            authorTemplate.send("topic-spring-1", "0", author)
                    .whenComplete((result, error) -> {
                        if (error == null) {
                            callback.accept(result);
                        } else
                            error.printStackTrace();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
