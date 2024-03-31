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
import java.util.List;
import java.util.Map;

public class IdempotentProducer {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    public static void main(String[] args) {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        new Thread(() -> {
            try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
            ))) {
                consumer.subscribe(List.of(TOPIC_1));

                int next = 0;
                while (!Thread.interrupted()) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        int current = record.key();
                        if (current != next) {
                            System.out.printf("ER: %d, AR: %d\n", next, current);
                        }
                        next = current + 1;
                    }
                }
            } catch (Exception ignored) {
            }
        }).start();

        try (Producer<Integer, Integer> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                //watch 'ss -tnp dport :9092'
                //sudo ss --kill dport :9092 sport :53796
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false//FIXME ENABLE_IDEMPOTENCE_CONFIG = true
        ))) {
            int i = 0;
            while (!Thread.interrupted()) {
                producer.send(new ProducerRecord<>(TOPIC_1, ++i, i),
                        (meta, error) -> {
                            if (error != null)
                                System.out.println(error.getMessage());
                        });
            }
        }
    }
}