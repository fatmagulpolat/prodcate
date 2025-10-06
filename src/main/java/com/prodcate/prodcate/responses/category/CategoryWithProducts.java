package com.prodcate.prodcate.responses.category;

import com.prodcate.prodcate.responses.product.GetProductDto;

import java.util.List;

public class CategoryWithProducts {
    String name;
    String description;
    List<GetProductDto> Products;
}
