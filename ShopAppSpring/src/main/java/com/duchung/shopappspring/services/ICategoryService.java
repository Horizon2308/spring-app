package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CategoryDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.models.Category;
import com.duchung.shopappspring.responses.CategoriesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO) throws DataExistedException;
    Category getCategoryById(Long categoryId);
    CategoriesResponse getAllCategory(Pageable pageable);
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long categoryId);
    boolean existedCategory(String categoryName);
}
