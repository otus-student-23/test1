package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.util.Util;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Replicas {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    public static void main(String[] args) {
        Util.recreateTopics(
                BOOTSTRAP_SERVERS,
                List.of(
                        new NewTopic(TOPIC_1, 2, (short) 2)
                                .configs(Map.of(
                                        TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, Integer.toString(2)
                                ))
                )
        );

        new Thread(() -> {
            try (Consumer<String, String> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class

                    //ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, List.of(RangeAssignor.class)//TODO
            ))) {
                consumer.subscribe(List.of(TOPIC_1));
                var records = consumer.poll(Duration.ofSeconds(5));
                records.forEach(it ->
                        System.out.printf("[RECV, %s.%s] %s:%s\n", it.topic(), it.partition(), it.key(), it.value())
                );
            }
        }).start();

        try (Producer<String, String> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,

                ProducerConfig.ACKS_CONFIG, "all"
        ))) {
            var record = new ProducerRecord<>(TOPIC_1, "0", "test");
            producer.send(
                    record,
                    (meta, error) -> System.out.printf(
                            "[SEND, %s.%s] %s:%s\n", meta.topic(), meta.partition(), meta.offset(), new Date(meta.timestamp())
                    )
            );
        }
    }
}