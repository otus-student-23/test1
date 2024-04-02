package org.example.service;

import org.example.dto.AuthorDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuthorListener {

    @KafkaListener(id = "myId", topics = "topic-spring-1")
    public void listen(AuthorDto author) {
        System.out.printf("[RECV] %s\n", author);
//        try {
//            Thread.sleep((long) (5_000 * Math.random()));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
