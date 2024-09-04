package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProduct();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(Long id, ProductUpdateRequest request);
    List<Product> getProductByCategory(Category category);
    List<Product> getProductByName(String name);
    Long countProduct();

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
//    List<Product> getProductByBrand(String brand);

}
