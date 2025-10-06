package com.prodcate.prodcate.mapper;

import com.prodcate.prodcate.entities.Product;
import com.prodcate.prodcate.requests.product.CreateProductRequest;
import com.prodcate.prodcate.responses.product.GetProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(CreateProductRequest createProductRequest);
    GetProductDto toProductDto(Product product);
    List<GetProductDto> toProductListDto(List<Product> products);
}
