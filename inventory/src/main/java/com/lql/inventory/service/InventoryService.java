package com.lql.inventory.service;


import com.lql.inventory.exception.ProductNotFoundException;
import com.lql.inventory.model.Inventory;
import com.lql.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isEnoughInStock(Inventory request) {
        String productId = request.getProductId();
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow( () -> new ProductNotFoundException(productId));
        return inventory.getQuantity() >= request.getQuantity();
    }

    public List<Inventory> isEnoughInStock(List<Inventory> requestList) {
        return requestList.stream()
                .filter(this::isEnoughInStock)
                .toList();
    }


    public List<Inventory> placeBulkOrder(List<Inventory> requestList) {

        List<Inventory> enoughInStock = isEnoughInStock(requestList);
        updateInventoryOffRequest(enoughInStock);

        return requestList.stream()
                .peek(i -> {
                    if(enoughInStock.stream().noneMatch(e -> e.getProductId().equals(i.getProductId())))
                        i.setQuantity(0);
                }).toList();
    }

    @Transactional
    @Async
    public void updateInventoryOffRequest( List<Inventory> enoughInStock) {
        List<Inventory> stock = inventoryRepository.findAllById(enoughInStock.stream().map(Inventory::getProductId).toList());
        inventoryRepository.saveAll(stock.parallelStream()
                .peek(i -> {
                    i.setQuantity(
                            i.getQuantity()
                                    -
                                    enoughInStock.parallelStream()
                                            .filter(r -> r.getProductId().equals(i.getProductId()))
                                            .findFirst().get().getQuantity());
                }).toList());
    }


    public Integer isInStock(String productId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        return inventory.map(Inventory::getQuantity).orElse(0);
    }

    public Iterable<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Transactional
    public Inventory setInventory(Inventory inventory) {
        Optional<Inventory> old = inventoryRepository.findByProductId(inventory.getProductId());
        if(old.isPresent()) {
            Inventory i = old.get();
            i.setQuantity(inventory.getQuantity());
            return inventoryRepository.save(i);
        }
        return inventoryRepository.save(inventory);
    }

}
