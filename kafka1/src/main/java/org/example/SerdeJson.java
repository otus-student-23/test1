package org.example;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.util.Util;
import org.example.dto.AuthorDto;
import org.example.serde.JsonDeserializer;
import org.example.serde.JsonSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SerdeJson {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    private static final Faker FAKER = new Faker(new Locale("ru"));

    public static void main(String[] args) {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        try (Producer<String, AuthorDto> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        ))) {
            producer.send(
                    new ProducerRecord<>(TOPIC_1, "0", new AuthorDto(FAKER.book().author()))
            );
        }

        try (Consumer<String, AuthorDto> consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",

                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                "class.deserializer", AuthorDto.class
        ))) {
            consumer.subscribe(List.of(TOPIC_1));
            var read = consumer.poll(Duration.ofSeconds(5));
            for (var record : read) {
                AuthorDto author = record.value();
                System.out.printf("[RECV] %s:%s\n", record.key(), author);
            }
        }
    }
}