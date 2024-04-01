package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerGroupMetadata;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.example.util.Util;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class TransactionReadWrite {

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
            Thread.currentThread().setName("transformer");
            String groupId = "transformer1";
            try (
                    Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                            ConsumerConfig.GROUP_ID_CONFIG, groupId,
                            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
                    ));
                    KafkaProducer<Integer, Integer> producer = new KafkaProducer<>(Map.of(
                            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                            ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction1"
                    ))
            ) {
                consumer.subscribe(List.of(TOPIC_1));
                producer.initTransactions();

                boolean hasNext = true;
                while (hasNext) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        producer.beginTransaction();
                        producer.send(new ProducerRecord<>(TOPIC_2, record.key(), record.value() * -1));
                        producer.sendOffsetsToTransaction(
                                Map.of(
                                        new TopicPartition(record.topic(), record.partition()),
                                        new OffsetAndMetadata(record.offset())),
                                new ConsumerGroupMetadata(groupId));
                        producer.commitTransaction();
                        hasNext = record.value() < maxValue;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            Thread.currentThread().setName("consumer");
            try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
            ))) {
                consumer.subscribe(List.of(TOPIC_2));
                boolean hasNext = true;
                while (hasNext) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        System.out.printf("[RECV] %s:%s\n", record.key(), record.value());
                        hasNext = record.value() > maxValue;
                    }
                }
            }
        }).start();

        try (var producer = new KafkaProducer<Integer, Integer>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class
        ))) {
            for (int i = 1; i <= maxValue; i++)
                producer.send(new ProducerRecord<>(TOPIC_1, i));
        }
    }
}
