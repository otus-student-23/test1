package org.example.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Class<T> clazz;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        clazz = (Class<T>) configs.get("class.deserializer");
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
