package org.example.util;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.errors.TopicExistsException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Util {

    public static void recreateTopics(String servers, List topics) {
        try (Admin admin = Admin.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, servers)
        )) {
            admin.deleteTopics(
                    admin.listTopics()
                            .listings()
                            .get()
                            .stream()
                            .map(TopicListing::name)
                            .toList()
            );
            while (true) {
                try {
                    admin.createTopics(topics).all().get();//.topicId(topic).get();
                    break;
                } catch (ExecutionException e) {
                    if (e.getCause() == null || e.getCause().getClass() != TopicExistsException.class)
                        throw e;
                    Thread.sleep(100);
                }
            }
            System.out.println("TOPICS RECREATED");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
