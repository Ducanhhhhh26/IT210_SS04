package com.restaurant.ss04005.repository;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    public String findOrderById(Long id) {
        return "Thông tin chi tiết của đơn hàng số: " + id;
    }

    public String createOrder() {
        return "Đơn hàng đã được tạo thành công!";
    }

    public String deleteOrder(Long id) {
        return "Đơn hàng " + id + " đã được hủy thành công.";
    }
}
