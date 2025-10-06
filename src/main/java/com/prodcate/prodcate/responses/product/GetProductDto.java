package com.prodcate.prodcate.responses.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  GetProductDto {
    String name;
    String description;
    int stock;
}
