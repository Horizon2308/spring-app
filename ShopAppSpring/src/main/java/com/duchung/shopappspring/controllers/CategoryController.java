package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.CategoryDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.Category;
import com.duchung.shopappspring.responses.CategoriesResponse;
import com.duchung.shopappspring.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        CategoriesResponse categoriesResponse = categoryService.getAllCategory(PageRequest.of(page, limit));
        if (categoriesResponse.getTotalPage() == 0 || categoriesResponse.getCategories().getContent().isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Category list is empty!"));
        }
        return ResponseEntity
                .ok()
                .body(new SuccessResponse<>(categoriesResponse, "Get all categories successfully!"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok().body(new SuccessResponse<>(categoryService.getCategoryById(categoryId),
                "Get category successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long categoryId,
                                                 @Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.accepted().body(new SuccessResponse<>(
                String.format("Update category that has id = %d", categoryId)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body(new SuccessResponse<>(
                String.format("Delete category that has id = %d", categoryId)
        ));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(new ErrorResponse<>(errorMessages));
        }
        try {
            categoryService.createCategory(categoryDTO);
        } catch (DataExistedException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                "Create category that has category name is: " +
                        categoryDTO.getName()
        ));
    }

}
