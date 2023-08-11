package com.lql.inventory.controller;

import com.lql.inventory.model.Inventory;
import com.lql.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory setInventory(@RequestBody Inventory inventory) {
        return inventoryService.setInventory(inventory);
    }


    @KafkaListener(topics = "orders")
    public void listen(ConsumerRecord<Long, List<Inventory>> record) {
        List<Inventory> list = record.value();

        kafkaTemplate.send("inventories", record.key(), inventoryService.placeBulkOrder(list));

        log.info("order placed successfully {}", list.toString());
    }


    @GetMapping
    public Iterable<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{productId}")
    public String isProductInStock(@PathVariable String productId) {
        int quantity = inventoryService.isInStock(productId);
        if ( quantity != 0) return String.format("Product %s has %s in stock", productId, quantity);

        return String.format("Product %s is not in stock", productId);
    }
    @GetMapping("/check-one")
    public boolean isEnoughInStock(@RequestBody Inventory checkInventoryRequest) {
        return inventoryService.isEnoughInStock(checkInventoryRequest);
    }

    @PostMapping("/place-orders")
    public List<Inventory> placeBulkOrder(@RequestBody List<Inventory> reqList) {
        return inventoryService.placeBulkOrder(reqList);
    }

}
