package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.model.Orders;

import java.util.List;

public interface OrderService {
    Orders placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Orders order);
}
