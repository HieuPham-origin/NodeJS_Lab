package com.example.demo.service;

import com.example.demo.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
