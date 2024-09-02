package com.example.demo.service;

import com.example.demo.model.CartItem;

public interface CartItemService {
    void addItemToCard(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemFromCart(Long cartId, Long productId, int quantity);

}
