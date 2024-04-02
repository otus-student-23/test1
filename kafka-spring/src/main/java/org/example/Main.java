package org.example;

import org.example.dto.AuthorDto;
import org.example.service.AuthorSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

/**
 * https://docs.spring.io/spring-kafka/reference/quick-tour.html
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var context = SpringApplication.run(Main.class, args);

        AuthorSender producer = context.getBean(AuthorSender.class);
        producer.send(new AuthorDto("author_a"),
                (it) -> System.out.printf("[SEND] %s:%s\n", it.getRecordMetadata().offset(), it.getProducerRecord().value())
        );
        int i = 0;
        while(!Thread.currentThread().isInterrupted()) {
            System.out.printf(
                    "[SEND] %s\n", producer.send(new AuthorDto("author_" + i++)).get().getProducerRecord().value()
            );
            Thread.sleep(1000);
        }

        Thread.sleep(2000);
    }
}