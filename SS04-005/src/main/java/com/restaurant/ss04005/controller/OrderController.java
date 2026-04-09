package com.restaurant.ss04005.controller;

import com.restaurant.ss04005.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // Sử dụng @Autowired thông qua Constructor
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Route 1: Xem đơn hàng - GET /orders/{id}
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable("id") Long id) {
        return orderService.getOrder(id);
    }

    // Route 2: Tạo đơn hàng - POST /orders
    @PostMapping
    public String createOrder() {
        return orderService.createOrder();
    }

    // Route 3: Hủy đơn hàng - DELETE /orders/{id}
    @DeleteMapping("/{id}")
    public String cancelOrder(@PathVariable("id") Long id) {
        return orderService.cancelOrder(id);
    }

    // Xử lý bẫy dữ liệu ép kiểu
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return "ID đơn hàng phải là một số (Lỗi: " + ex.getValue() + " không hợp lệ).";
    }
}
