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

public class TransactionIsolationLevel {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final String TOPIC_1 = "topic1";

    public static void main(String[] args) throws Exception {
        Util.recreateTopics(BOOTSTRAP_SERVERS, List.of(new NewTopic(TOPIC_1, 1, (short) 1)));

        Thread thread = new Thread(() -> {
            try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer1",
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,

                    ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed"
            ))) {
                consumer.subscribe(List.of(TOPIC_1));
                while (!Thread.interrupted()) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        System.out.printf("[RECV, %s] %s:%s\n", Thread.currentThread().getName(), record.key(), record.value());
                    }
                }
            }
        });
        thread.setName("read_committed");
        thread.setDaemon(true);
        thread.start();

        thread = new Thread(() -> {
            try (Consumer<Integer, Integer> consumer = new KafkaConsumer<>(Map.of(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                    ConsumerConfig.GROUP_ID_CONFIG, "consumer2",
                    ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,

                    ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_uncommitted"
            ))) {
                consumer.subscribe(List.of(TOPIC_1));
                while (!Thread.interrupted()) {
                    var read = consumer.poll(Duration.ofSeconds(1));
                    for (var record : read) {
                        System.out.printf("[RECV, %s] %s:%s\n", Thread.currentThread().getName(), record.key(), record.value());
                    }
                }
            }
        });
        thread.setName("read_uncommitted");
        thread.setDaemon(true);
        thread.start();

        try (
                var producer = new KafkaProducer<Integer, Integer>(Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class
                ));
                var producerTransactional = new KafkaProducer<Integer, Integer>(Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,

                        ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_1"
                ))
        ) {
            producerTransactional.initTransactions();

            System.out.println("beginTransaction");
            producerTransactional.beginTransaction();
            Thread.sleep(500);
            producer.send(new ProducerRecord<>(TOPIC_1, -1)); // вне транзакции - оба получат
            Thread.sleep(500);
            producerTransactional.send(new ProducerRecord<>(TOPIC_1, 1)); // сразу получит только read_uncommitted
            Thread.sleep(500);
            producer.send(new ProducerRecord<>(TOPIC_1, -2)); // сразу получит только read_uncommitted, хотя вне транзакции
            Thread.sleep(500);
            producerTransactional.send(new ProducerRecord<>(TOPIC_1, 2)); // сразу получит только consumerRUnC
            Thread.sleep(500);
            System.out.println("commitTransaction");
            producerTransactional.commitTransaction(); // read_committed получит 1,2,3

            System.out.println("beginTransaction");
            producerTransactional.beginTransaction();
            producerTransactional.send(new ProducerRecord<>(TOPIC_1, 3)); // получит только consumerRUnC (abort)
            Thread.sleep(500);
            System.out.println("abortTransaction");
            producerTransactional.abortTransaction();

            producer.send(new ProducerRecord<>(TOPIC_1, 4)); // получат оба

            Thread.sleep(1000);
        }
    }
}
