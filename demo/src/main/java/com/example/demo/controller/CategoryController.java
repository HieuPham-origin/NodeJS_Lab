package com.example.demo.controller;

import com.example.demo.exception.AlreadyExistException;
import com.example.demo.model.Category;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
        try {
            Category theCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Add success", theCategory));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/category/{categoryId}/get")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        Category theCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponse("get category " + categoryId +" success", theCategory));
    }
    @GetMapping("/category/{name}/get")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        Category theCategory = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("get category " + name +" success", theCategory));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("delete category " + id +" success", null));
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,
                                                      @RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(category, id);
        return ResponseEntity.ok(new ApiResponse("update category " + id +" success", updatedCategory));
    }
}
