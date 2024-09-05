package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.model.Orders;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        Orders order = orderService.placeOrder(userId);
        OrderDto orderDto = orderService.convertToDto(order);
        return ResponseEntity.ok(new ApiResponse("success order", orderDto));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        OrderDto order = orderService.getOrder(orderId);

        return ResponseEntity.ok(new ApiResponse("get order success", order));
    }
    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable Long userId){
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("get order success", orders));
    }
}
