package com.lql.orderservice.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lql.orderservice.model.Inventory;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class MyConverter implements Deserializer<List<Inventory>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<Inventory> deserialize(String s, byte[] bytes) {
        List<Inventory> inventories = null;

        try{
            inventories = objectMapper.readValue(bytes, new TypeReference<List<Inventory>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }


        return inventories;
    }
}
