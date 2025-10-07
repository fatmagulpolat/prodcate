package com.prodcate.prodcate.exception;

public class ProductNotFoundException extends NotFoundException{
    public ProductNotFoundException(String productId) {

        super(productId+"Id'li ürün bulunamadı");
    }
}
