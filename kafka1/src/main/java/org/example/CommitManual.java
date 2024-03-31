package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.example.util.Util;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommitManual {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC = "topic1";

    public static void main(String[] args) {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC, 1, (short) 1)));

        int maxValue = 10;

        try (Producer<Integer, Integer> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class
        ))) {
            for (int i = 1; i <= maxValue; i++) {
                producer.send(
                        new ProducerRecord<>(TOPIC, 0, i),
                        (meta, error) -> System.out.printf("[SEND] %s:%s\n", meta.offset(), new Date(meta.timestamp()))
                );
            }
        }

        System.out.println("-------------------------");
        try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,

                //ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3,
                //ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 7,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false,
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2
        ))) {
            consumer.subscribe(List.of(TOPIC));

            boolean hasNext = true;
            while (hasNext) {
                var read = consumer.poll(Duration.ofSeconds(1));
                var value = 0;
                for (var record : read) {
                    value = record.value();
                    System.out.printf("[RECV] %s:%s\n", record.key(), value);
                    hasNext = value < maxValue - 1;
                }
                if (value > 0 && value < maxValue / 2) {
                    consumer.commitSync();
                }
            }
        }

        System.out.println("-------------------------");
        try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
        ))) {
            consumer.subscribe(List.of(TOPIC));

            boolean hasNext = true;
            while (hasNext) {
                var read = consumer.poll(Duration.ofSeconds(2));
                for (var record : read) {
                    System.out.printf("[RECV] %s:%s\n", record.key(), record.value());
                    hasNext = record.value() < maxValue;
                }
            }
        }
    }
}