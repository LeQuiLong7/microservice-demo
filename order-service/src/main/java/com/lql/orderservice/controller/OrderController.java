package com.lql.orderservice.controller;


import com.lql.orderservice.model.Order;
import com.lql.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrders();
    }
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
