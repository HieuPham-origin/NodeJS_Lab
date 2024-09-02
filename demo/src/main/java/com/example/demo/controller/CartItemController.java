package com.example.demo.controller;


import com.example.demo.response.ApiResponse;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartService cartService;
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam (required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
        if (cartId == null ){
            cartId = cartService.initNewCart();

        }
        cartItemService.addItemToCard(cartId, productId, quantity);
        return ResponseEntity.ok(new ApiResponse("add item success", null));
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
