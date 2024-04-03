package org.example;

import com.github.javafaker.Faker;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
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

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SerdeAvro {

    private static final String BOOTSTRAP_SERVERS = "localhost:9091";

    private static final String TOPIC_1 = "topic2";

    private static final Faker FAKER = new Faker(new Locale("ru"));

    public static void main(String[] args) {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        try (Producer<String, GenreDto> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,

                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class,
                KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"
        ))) {
            producer.send(
                    new ProducerRecord<>(TOPIC_1, "2",
                            GenreDto.newBuilder()
                                    .setName(FAKER.book().genre())
                                    .build()
                    ), (meta, error) -> {
                        if (error != null) error.printStackTrace();
                    }
            );
        }

        try (Consumer<String, GenreDto> consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,

                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class,
                KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true,
                KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"
        ))) {
            consumer.subscribe(List.of(TOPIC_1));
            var records = consumer.poll(Duration.ofSeconds(5));
            records.forEach(it -> {
                GenreDto genre = it.value();
                System.out.printf("[RECV] %s:%s\n", it.key(), genre);
            });
        }
    }
}
