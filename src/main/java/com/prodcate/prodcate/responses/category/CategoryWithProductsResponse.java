package com.prodcate.prodcate.responses.category;

import com.prodcate.prodcate.responses.product.GetProductDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryWithProductsResponse {
    String name;
    String description;
    List<GetProductDto> Products;
}
