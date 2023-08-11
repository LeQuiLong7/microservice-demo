package com.lql.inventory.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String productId) {
        super(String.format("Product with id %s is not exits", productId));
    }
}
