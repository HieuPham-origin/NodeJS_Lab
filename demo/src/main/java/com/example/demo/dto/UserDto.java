package com.example.demo.dto;

import com.example.demo.model.Cart;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private Cart cart;
}
