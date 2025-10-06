package com.prodcate.prodcate.controllers;

import com.prodcate.prodcate.requests.product.CreateProductRequest;
import com.prodcate.prodcate.requests.product.UpdateProductRequest;
import com.prodcate.prodcate.responses.product.GetProductDto;
import com.prodcate.prodcate.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity createProduct(@RequestBody CreateProductRequest createProductRequest){
        productService.create(createProductRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductDto> getProductById(@PathVariable String id){
        GetProductDto getProductDto = productService.getProduct(id);
        return ResponseEntity.ok(getProductDto);
    }

    @GetMapping()
    public ResponseEntity<List<GetProductDto>> getProductsByCategoryId(@RequestParam String categoryId){
        List<GetProductDto> productDtoList = productService.getProductsByCatogryId(categoryId);
        return ResponseEntity.ok(productDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<GetProductDto>> updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest){
        productService.updateProduct(id, updateProductRequest);
        return ResponseEntity.noContent().build();
    }

}
