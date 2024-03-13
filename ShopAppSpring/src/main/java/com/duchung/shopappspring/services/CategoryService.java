package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.CategoryDTO;
import com.duchung.shopappspring.models.Category;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        if (existedCategory(categoryDTO.getName())) {
            throw new RuntimeException("Category existed!");
        }
        return categoryRepository.save(new Category().builder().name(categoryDTO.getName()).build());
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public boolean existedCategory(String categoryName) {
        return categoryRepository.existsCategoryByName(categoryName);
    }

    // using to turn option<category> to category
//    private Category categoryWrapper(Optional<Category> wrapCategory) {
//        if (wrapCategory.isPresent()) {
//            return wrapCategory.get();
//        }
//        return null;
//    }
}
