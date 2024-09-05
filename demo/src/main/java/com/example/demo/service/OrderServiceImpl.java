package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.model.Orders;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    private final ModelMapper modelMapper;
    @Override
    public Orders placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Orders order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculatePrice(orderItemList));
        Orders savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Orders createOrder(Cart cart){
        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItem(Orders order, Cart cart){
        return cart.getItems().stream().map(cartItem ->{
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    private BigDecimal calculatePrice(List<OrderItem> orderItems){
        return orderItems.stream().map(item->item.getPrice().multiply(new BigDecimal(item.getQuantity()))).
                reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository
                .findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Could find order"));
    }
    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Orders> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }
    @Override
    public OrderDto convertToDto(Orders order){
        return modelMapper.map(order, OrderDto.class);
    }
}
