package com.example.demo.service;

import com.example.demo.model.Cart;
import com.example.demo.model.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initNewCart();

    Cart initNewCart(User user);

    Cart getCartByUserId(Long userId);
}
