package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
        Order order = orderService.placeOrder(userId);
        return ResponseEntity.ok(new ApiResponse("success order", order));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("get order success", order));
    }
    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable Long userId){
        List<Order> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("get order success", orders));
    }
}
