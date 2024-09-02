package com.example.demo.service;

import com.example.demo.model.Order;

public interface OrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
