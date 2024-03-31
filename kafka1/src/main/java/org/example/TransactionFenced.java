package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.example.util.Util;

import java.util.List;
import java.util.Map;

public class TransactionFenced {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    public static void main(String[] args) throws Exception {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        try (var producer = new KafkaProducer<Integer, Integer>(Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction1"
        ))) {
            producer.initTransactions();
            producer.beginTransaction();
            producer.send(new ProducerRecord<>(TOPIC_1, 1));
            producer.commitTransaction();

            try (var producer2 = new KafkaProducer<Integer, Integer>(Map.of(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                    ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction1"
            ))) {
                producer2.initTransactions();
                producer2.beginTransaction();
                producer2.send(new ProducerRecord<>(TOPIC_1, -2));
                producer2.commitTransaction();
            }

            try {
                producer.beginTransaction();
                producer.send(new ProducerRecord<>(TOPIC_1, 3));
                producer.commitTransaction();
            } catch (ProducerFencedException e) {
                e.printStackTrace();
            }

            Thread.sleep(500);
        }
    }
}
