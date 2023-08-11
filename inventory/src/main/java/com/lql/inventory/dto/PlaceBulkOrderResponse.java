package com.lql.inventory.dto;

import com.lql.inventory.model.Inventory;

import java.util.List;

public record PlaceBulkOrderResponse (List<Inventory> list) {
}
