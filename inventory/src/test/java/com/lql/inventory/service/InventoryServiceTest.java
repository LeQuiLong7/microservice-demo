package com.lql.inventory.service;

import com.lql.inventory.model.Inventory;
import com.lql.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
@DataJpaTest(showSql = true)
@Import(InventoryService.class)
class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryRepository inventoryRepository;


    @Test
    void isEnoughInStock() {
        Inventory inventory = new Inventory("1", 8);
        Inventory inventory1 = new Inventory("2", 10);
        Inventory inventory2 = new Inventory("3", 8);
        List<Inventory> inventoryList = List.of(inventory, inventory1, inventory2);
        List<Inventory> enoughInStock = inventoryService.isEnoughInStock(inventoryList);

        assertEquals(enoughInStock.size(), 2);
        assertTrue(enoughInStock.contains(inventory1));
    }

    @Test
    void placeBulkOrder(){
        Inventory inventory = new Inventory("1", 4);
        Inventory inventory1 = new Inventory("2", 10);
        Inventory inventory2 = new Inventory("3", 8);
        List<Inventory> orderList = List.of(inventory, inventory1, inventory2);


        List<Inventory> before = inventoryRepository.findAllById(
                orderList.stream().map(Inventory::getProductId).toList()
        );

        List<Inventory> ordered = inventoryService.placeBulkOrder(orderList);

        List<Inventory> after = inventoryRepository.findAllById(
                orderList.stream().map(Inventory::getProductId).toList()
        );


        assertEquals(ordered.size(), orderList.size());
        assertEquals(before.size(), after.size());
        assertEquals(1, getQuantityById(after, "1"));
        assertEquals(0, getQuantityById(after, "2"));
        assertEquals(1, getQuantityById(after, "3"));
        assertEquals(4, getQuantityById(ordered, "1"));
        assertEquals(10, getQuantityById(ordered, "2"));
        assertEquals(0, getQuantityById(ordered, "3"));
    }

    public int getQuantityById(List<Inventory> list, String id) {
        return list.parallelStream().filter(i -> i.getProductId().equals(id)).findFirst().get().getQuantity();
    }

}