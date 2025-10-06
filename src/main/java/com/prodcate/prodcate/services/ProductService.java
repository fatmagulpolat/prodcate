package com.prodcate.prodcate.services;

import com.prodcate.prodcate.entities.Category;
import com.prodcate.prodcate.entities.Product;
import com.prodcate.prodcate.mapper.ProductMapper;
import com.prodcate.prodcate.repositories.CategoryRepository;
import com.prodcate.prodcate.repositories.ProductRepository;
import com.prodcate.prodcate.requests.product.CreateProductRequest;
import com.prodcate.prodcate.requests.product.UpdateProductRequest;
import com.prodcate.prodcate.responses.product.GetProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public void create(CreateProductRequest createProductRequest){
       Product product = productMapper.toProduct(createProductRequest);
       Category category = categoryRepository.findById(createProductRequest.getCategoryId()).orElseThrow(() -> new RuntimeException());
       product.setCategory(category);
       productRepository.save(product);
    }

    public GetProductDto getProduct(String id){
        UUID productId = UUID.fromString(id);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException());
        return productMapper.toProductDto(product);
    }

    public List<GetProductDto> getProductsByCatogryId(String categoryId) {
        UUID id = UUID.fromString(categoryId);
        List<Product> products = productRepository.findByCategoryId(id);
        if (products.isEmpty())
            throw new RuntimeException();

        return productMapper.toProductListDto(products);
    }

    public void updateProduct(String id, UpdateProductRequest updateProductRequest){
        UUID productId = UUID.fromString(id);
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException());
        Category category = categoryRepository.findById( UUID.fromString(updateProductRequest.getCategoryId())).orElseThrow(()-> new RuntimeException());
        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setStock(updateProductRequest.getStock());
        product.setCategory(category);

        productRepository.save(product);
    }
}
