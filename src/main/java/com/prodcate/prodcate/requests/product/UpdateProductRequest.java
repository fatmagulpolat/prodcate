package com.prodcate.prodcate.requests.product;

import lombok.Data;

@Data
public class UpdateProductRequest {
    String name;
    String description;
    int stock;
    String categoryId;
}
