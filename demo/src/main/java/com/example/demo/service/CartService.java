package com.example.demo.service;

import com.example.demo.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initNewCart();

    Cart getCartByUserId(Long userId);
}
