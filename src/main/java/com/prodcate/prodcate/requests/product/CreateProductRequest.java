package com.prodcate.prodcate.requests.product;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductRequest {

   private String name;
   private String description;
   private int stock;
   private UUID categoryId;
}
