package com.prodcate.prodcate.services;

import com.prodcate.prodcate.common.response.ResponseBase;
import com.prodcate.prodcate.entities.Category;
import com.prodcate.prodcate.entities.Product;
import com.prodcate.prodcate.exception.CategoryNotFoundException;
import com.prodcate.prodcate.exception.ProductNotFoundException;
import com.prodcate.prodcate.mapper.ProductMapper;
import com.prodcate.prodcate.repositories.CategoryRepository;
import com.prodcate.prodcate.repositories.ProductRepository;
import com.prodcate.prodcate.requests.product.CreateProductRequest;
import com.prodcate.prodcate.requests.product.UpdateProductRequest;
import com.prodcate.prodcate.responses.product.GetProductDto;
import org.springframework.stereotype.Service;
import java.util.List;
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
       UUID categoryId = UUID.fromString(createProductRequest.getCategoryId());
       Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotFoundException(createProductRequest.getCategoryId()));
       product.setCategory(category);
       productRepository.save(product);
    }

    public ResponseBase<GetProductDto> getProduct(String id){
        UUID productId = UUID.fromString(id);
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(id));
        GetProductDto productDto = productMapper.toProductDto(product);

        return ResponseBase.success(productDto);
    }

    public ResponseBase<List<GetProductDto>> getProductsByCatogryId(String categoryId) {
        UUID id = UUID.fromString(categoryId);
        List<Product> products = productRepository.findByCategoryId(id);
        if (products.isEmpty())
            throw new RuntimeException();

        List<GetProductDto> productDtoList = productMapper.toProductListDto(products);
        return ResponseBase.success(productDtoList);
    }

    public void updateProduct(String id, UpdateProductRequest updateProductRequest){
        UUID productId = UUID.fromString(id);
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(id));
        UUID categoryId =  UUID.fromString(updateProductRequest.getCategoryId());
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotFoundException(updateProductRequest.getCategoryId()));
        product.setName(updateProductRequest.getName());
        product.setDescription(updateProductRequest.getDescription());
        product.setStock(updateProductRequest.getStock());
        product.setCategory(category);

        productRepository.save(product);
    }
}
