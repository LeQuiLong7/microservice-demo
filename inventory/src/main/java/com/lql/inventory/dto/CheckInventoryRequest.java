package com.lql.inventory.dto;

public record CheckInventoryRequest (String productId, int quantity) {
}
