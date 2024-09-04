package com.example.demo.controller;


import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import com.example.demo.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam (required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
//        if (cartId == null ){
//            cartId = cartService.initNewCart();
//        }
//        cartItemService.addItemToCard(cartId, productId, quantity);

        try {
            User user = userService.getAuthenticationUser();
            Cart cart = cartService.initNewCart(user);
            cartItemService.addItemToCard(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("add item success", null));
        } catch (JwtException e) {
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                     @PathVariable Long itemId){
        cartItemService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.ok(new ApiResponse("remove item success", null));
    }
    @PutMapping("/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                    @PathVariable Long itemId,
                                                    @RequestParam Integer quantity){
        cartItemService.updateItemFromCart(cartId, itemId, quantity);
        return ResponseEntity.ok(new com.example.demo.response.ApiResponse("update item success", null));
    }
}
