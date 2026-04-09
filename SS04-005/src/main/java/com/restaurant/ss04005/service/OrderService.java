package com.restaurant.ss04005.service;

import com.restaurant.ss04005.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // Tiêm qua Constructor - Constructor Injection
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String getOrder(Long id) {
        return orderRepository.findOrderById(id);
    }

    public String createOrder() {
        return orderRepository.createOrder();
    }

    public String cancelOrder(Long id) {
        return orderRepository.deleteOrder(id);
    }
}
