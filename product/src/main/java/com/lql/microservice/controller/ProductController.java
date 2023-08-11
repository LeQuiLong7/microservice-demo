package com.lql.microservice.controller;


import com.lql.microservice.model.Product;
import com.lql.microservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product product) {
        log.info("create new product {}", product);
        return productService.create(product);
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        log.info("get all product");
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        log.info("get product with id {}", id);
        return productService.getProductById(id);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("update product {}", product);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable String id) {
        log.info("delete product with id {}", id);
        productService.deleteProductById(id);
        return "deleted";
    }
}
