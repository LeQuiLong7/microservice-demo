package com.lql.microservice.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String id) {
        super(String.format("Product %s is not exits", id));
    }
}
