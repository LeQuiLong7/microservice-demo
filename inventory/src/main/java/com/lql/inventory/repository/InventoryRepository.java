package com.lql.inventory.repository;

import com.lql.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
    Inventory findInventoryByProductIdAndQuantityGreaterThanEqual(String productId, Integer quantity);
}
