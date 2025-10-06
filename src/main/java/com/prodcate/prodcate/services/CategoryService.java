package com.prodcate.prodcate.services;

import com.prodcate.prodcate.entities.Category;
import com.prodcate.prodcate.mapper.CategoryMapper;
import com.prodcate.prodcate.repositories.CategoryRepository;
import com.prodcate.prodcate.requests.category.CreateCategoryRequest;
import com.prodcate.prodcate.requests.category.UpdateCategoryRequest;
import com.prodcate.prodcate.responses.category.CategoryResponse;
import com.prodcate.prodcate.responses.category.CategoryWithProductsResponse;
import com.prodcate.prodcate.responses.product.GetProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public void createCategory(CreateCategoryRequest categoryRequest){
        Category category = categoryMapper.toCategory(categoryRequest);
        categoryRepository.save(category);
    }

    public void updateCategory(String id, UpdateCategoryRequest categoryRequest){
        UUID categoryId = UUID.fromString(id);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException());
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getCategoryList(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponse = categoryMapper.toCategoryResponseList(categories);
        return categoryResponse;
    }

    public CategoryResponse getCategoryById(String id) {
        UUID categoryId = UUID.fromString(id);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException());
        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryWithProductsResponse getCategoryWithProducts(String id) {
        UUID categoryId = UUID.fromString(id);
        Category byIdWithProducts = categoryRepository.findByIdWithProducts(categoryId).orElseThrow(() -> new RuntimeException("hata buradan"));

        CategoryWithProductsResponse response = new CategoryWithProductsResponse();
        response.setDescription(byIdWithProducts.getDescription());
        response.setName(byIdWithProducts.getName());
        List<GetProductDto> products = byIdWithProducts.getProducts()
                .stream()
                .map(p->new GetProductDto(
                        p.getName(),
                        p.getDescription(),
                        p.getStock())).toList();

        response.setProducts(products);
        return response;
    }
}
