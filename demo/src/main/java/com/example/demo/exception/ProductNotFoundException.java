package com.example.demo.exception;

import com.example.demo.service.ProductService;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }
}
