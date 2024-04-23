package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.ProductDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.http_responses.BaseResponse;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.responses.ProductImageResponse;
import com.duchung.shopappspring.responses.ProductListResponse;
import com.duchung.shopappspring.responses.ProductResponse;
import com.duchung.shopappspring.services.ICategoryService;
import com.duchung.shopappspring.services.IProductImageService;
import com.duchung.shopappspring.services.IProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IProductImageService productImageService;

    @GetMapping()
    public ResponseEntity<BaseResponse<ProductListResponse>> getAllProducts(
            @RequestParam(defaultValue = "0", value = "category_id") Long categoryId,
            @RequestParam(defaultValue = "", value = "keyword") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12") Integer limit) {
        Page<ProductResponse> products = productService.getAllProducts(
                categoryId,
                keyword,
                PageRequest.of(page, limit,
                Sort.by("id").ascending()));
        int totalPage = products.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Products page is empty!"));
        }
        if (products.getContent().size() == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Products page is empty!"));
        }
        return ResponseEntity.ok().body(new SuccessResponse<>(ProductListResponse.builder()
                .productResponses(products)
                .totalPage(totalPage)
                .build(),
                "Get all products"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        try {
            ProductResponse productResponse = productService.getProductById(productId);
            return ResponseEntity.ok(new SuccessResponse<>(productResponse, "Get product successfully"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductsByIds(@RequestParam("ids") String ids) {
        List<Long> productIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        List<Product> products = productService.findProductsByIds(productIds);
        return ResponseEntity.ok().body(products);
    }
    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                            BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            ProductResponse productResponse = productService.createProduct(productDTO);
            return ResponseEntity.ok(new SuccessResponse<>(productResponse, "Created"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") Long productId,
                                          @ModelAttribute("listOfFiles") List<MultipartFile> listOfFiles) {
        try {
            List<ProductImageResponse> productImageResponses =
                    productImageService.saveAllProductImages(productId, listOfFiles);
            return ResponseEntity.ok().body(new SuccessResponse<>(productImageResponses,
                    "Uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @GetMapping("images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        Path imagePath = Paths.get("uploads/" + imageName);
        try {
            UrlResource urlResource = new UrlResource(imagePath.toUri());
            if (urlResource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(urlResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long productId) {
        return ResponseEntity.accepted().body("Update product has id: " + productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new SuccessResponse<>("Deleted"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/fake-data")
    public void fakeData() {
        Faker faker = new Faker();
        for (int i = 0; i < 10000; i++) {
            try {
                productService.createProduct(ProductDTO.builder()
                                .name(faker.commerce().productName())
                                .price(Double.parseDouble(faker.commerce().price(0, 10_000_000)))
                                .thumbnail("")
                                .description(faker.lorem().sentence())
                                .categoryId(Long.parseLong(String.valueOf(faker.number().numberBetween(1, 4))))
                        .build());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
