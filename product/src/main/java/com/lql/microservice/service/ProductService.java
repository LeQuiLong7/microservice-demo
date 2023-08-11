package com.lql.microservice.service;

import com.lql.microservice.exception.ProductNotFoundException;
import com.lql.microservice.model.Product;
import com.lql.microservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(Product product) {
        return productRepository.insert(product);
    }

    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(Product.builder()
                .id("undefined")
                .name("undefined")
                .price(new BigDecimal(-1))
                .description("_")
                .build());
    }

    public void deleteProductById(String id) {
        if (!productRepository.existsById(id)) throw new ProductNotFoundException(id);

        productRepository.deleteById(id);
    }


    public Product updateProduct(Product product) {
        Product product1 = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(product.getId()));

        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDescription(product.getDescription());
        return productRepository.save(product1);
    }
}
