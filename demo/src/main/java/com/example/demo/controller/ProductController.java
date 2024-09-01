package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.request.AddProductRequest;
import com.example.demo.request.ProductUpdateRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(new ApiResponse("get all product success", products));
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse("get product " + id + " success", product));
    }

    @PostMapping("/add")
    public   ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        Product theProduct = productService.addProduct(product);
        return ResponseEntity.ok(new ApiResponse("Add product success", product));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest,
                                                     @PathVariable Long id){
        Product product = productService.updateProduct(id, productUpdateRequest);
        return ResponseEntity.ok(new ApiResponse("update product success", product));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(new ApiResponse("delete success", id));
    }
}
