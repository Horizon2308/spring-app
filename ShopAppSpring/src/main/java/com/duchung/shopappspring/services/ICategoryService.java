package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CategoryDTO;
import com.duchung.shopappspring.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategory();
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long categoryId);
    boolean existedCategory(String categoryName);
}
