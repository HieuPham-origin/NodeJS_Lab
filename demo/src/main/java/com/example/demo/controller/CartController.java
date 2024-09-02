package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(new ApiResponse("success to get cart", cart));
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.ok(new ApiResponse("clear cart successful", null));
    }
    @GetMapping("/{cartId}/totalAmount")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        return ResponseEntity.ok(new ApiResponse("Total price", totalPrice));
    }
}
