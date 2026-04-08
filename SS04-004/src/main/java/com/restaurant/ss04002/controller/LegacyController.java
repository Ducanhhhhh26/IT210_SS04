package com.restaurant.ss04002.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.restaurant.ss04002.service.OrderService;

@Controller("legacyControllerBai1")
public class LegacyController {

    private final OrderService orderService;

    public LegacyController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    @ResponseBody
    public String getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    public String getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/bai2/menu")
    @ResponseBody
    public String getMenu(@RequestParam(name = "category", required = false, defaultValue = "chay") String category) {
        return "Menu cho loai: " + category;
    }

    @GetMapping("/bai3/orders/{id}")
    @ResponseBody
    public String getOrderDetailsBai3(@PathVariable Long id) {
        return "Chi tiết đơn hàng số " + id;
    }

    @GetMapping("/bai4/products")
    public String getProductList(
            @RequestParam(name = "category", required = false, defaultValue = "chay") String category,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            ModelMap modelMap) {
        modelMap.addAttribute("category", category).addAttribute("limit", limit).addAttribute("message", "Tìm kiếm thành công");
        return "productList";
    }

}
