package com.prodcate.prodcate.mapper;

import com.prodcate.prodcate.entities.Category;
import com.prodcate.prodcate.requests.category.CreateCategoryRequest;
import com.prodcate.prodcate.requests.category.UpdateCategoryRequest;
import com.prodcate.prodcate.responses.category.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
   Category toCategory(CreateCategoryRequest request);
   List<CategoryResponse> toCategoryResponseList(List<Category> categoryList);
   CategoryResponse toCategoryResponse(Category category);
}
