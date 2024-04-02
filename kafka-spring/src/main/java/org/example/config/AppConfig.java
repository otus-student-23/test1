package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.JacksonUtils;

@Configuration
public class AppConfig {

    @Bean
    //создание единого на всю систему ObjectMapper (на всех)
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("topic-spring-1")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
