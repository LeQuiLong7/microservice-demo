package com.lql.orderservice.service;


import com.lql.orderservice.model.Inventory;
import com.lql.orderservice.model.Order;
import com.lql.orderservice.model.OrderItems;
import com.lql.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    public String fallback(Order order, RuntimeException exception) {
        return "Oops, something went wrong!";
    }

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
    public String placeOrder(Order order) {

        List<Inventory> inventories = order.getOrderItems().stream()
                .map(orderItems -> new Inventory(orderItems.getProductId(), orderItems.getQuantity()))
                .toList();

        order.setOrderItems(null);
        orderRepository.saveAndFlush(order);
        System.err.println(order.getId());
        kafkaTemplate.send("orders", order.getId(), inventories)
                .thenAccept(o -> System.out.printf("order_id %d placed successfully%n", order.getId()))
                .exceptionally(throwable -> {
                    System.out.println(throwable.getClass());
                    return null; });

        return "Order place successfully";

    }

    @KafkaListener(topics = "inventories")
    public void saveOrder(ConsumerRecord<Long, List<Inventory>> record) {

        Order order = orderRepository.findById(record.key()).get();

        order.setOrderItems(record.value().parallelStream()
                .map(i -> new OrderItems(i.productId(), i.quantity()))
                .toList()
        );
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {return orderRepository.findAll();}

    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(new Order(-1L,"undefined", null));
    }

}
