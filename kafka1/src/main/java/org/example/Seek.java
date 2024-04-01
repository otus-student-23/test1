package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.example.util.Util;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Seek {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    public static void main(String[] args) {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        int maxValue = 10;

        try (Producer<Integer, Integer> producer = new KafkaProducer<>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class
        ))) {
            for (int i = 1; i <= maxValue; i++) {
                producer.send(new ProducerRecord<>(TOPIC_1, 0, i));
            }
        }

        try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class
        ))) {
            consumer.subscribe(List.of(TOPIC_1), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    consumer.seekToBeginning(List.of(new TopicPartition(TOPIC_1, 0)));
                }
            });

            boolean hasNext = true;
            while (hasNext) {
                var records = consumer.poll(Duration.ofSeconds(1));
                for (var record : records) {
                    System.out.printf("[RECV] %s:%s\n", record.key(), record.value());
                    hasNext = record.value() < maxValue;
                }
            }
        }
    }
}