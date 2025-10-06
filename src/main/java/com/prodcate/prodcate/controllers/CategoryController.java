package com.prodcate.prodcate.controllers;

import com.prodcate.prodcate.requests.category.CreateCategoryRequest;
import com.prodcate.prodcate.requests.category.UpdateCategoryRequest;
import com.prodcate.prodcate.responses.category.CategoryResponse;
import com.prodcate.prodcate.responses.category.CategoryWithProductsResponse;
import com.prodcate.prodcate.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController{
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String id){
       CategoryResponse categoryResponse = categoryService.getCategoryById(id);
       return ResponseEntity.ok(categoryResponse);
    }
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategoryList(){
        List<CategoryResponse> categoryList = categoryService.getCategoryList();
        return ResponseEntity.ok(categoryList);
    };

    @GetMapping("/{id}/with-products")
    public ResponseEntity<CategoryWithProductsResponse> getCategoryWithProducts(@PathVariable String id){
        CategoryWithProductsResponse categoryWithProducts = categoryService.getCategoryWithProducts(id);
        return ResponseEntity.ok(categoryWithProducts);
    }
    @PostMapping
    public void create(@RequestBody CreateCategoryRequest categoryRequest){
        categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/{id}")
    public void create(@PathVariable String id, @RequestBody UpdateCategoryRequest categoryRequest){
        categoryService.updateCategory(id, categoryRequest);
    }
}
