package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.example.util.Util;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class TransactionSimple {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    private static final String TOPIC_2 = "topic2";

    public static void main(String[] args) {
        Util.recreateTopics(
                BOOTSTRAP_SERVERS,
                List.of(
                        new NewTopic(TOPIC_1, 1, (short) 1),
                        new NewTopic(TOPIC_2, 1, (short) 1)
                )
        );

        int maxValue = 5;

        new Thread(() -> {
            try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
            ))) {
                consumer.subscribe(List.of(TOPIC_1));

                boolean hasNext = true;
                while (hasNext) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        System.out.printf("[RECV, %s.%s] %s:%s\n", record.topic(), record.partition(), record.key(), record.value());
                        hasNext = record.value() < maxValue;
                    }
                }
            }
        }).start();

        try (var producer = new KafkaProducer<Integer, Integer>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_1"
        ))) {
            producer.initTransactions();

            for (int i = 0; i <= maxValue; ++i) {
                producer.beginTransaction();
                producer.send(new ProducerRecord<>(TOPIC_1, i));
                producer.send(new ProducerRecord<>(TOPIC_2, i * 10));
                producer.commitTransaction();
            }
        }
    }
}