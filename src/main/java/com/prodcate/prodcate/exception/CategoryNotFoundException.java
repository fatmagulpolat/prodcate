package com.prodcate.prodcate.exception;

public class CategoryNotFoundException extends NotFoundException {

    public CategoryNotFoundException(String categoryId) {

        super(categoryId+"Id'li kategori bulunamadı");
    }
}
