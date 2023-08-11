package com.lql.orderservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String productId;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderItems(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
