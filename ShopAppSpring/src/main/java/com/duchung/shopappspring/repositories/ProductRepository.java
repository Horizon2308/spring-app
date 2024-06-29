package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsProductByName(String productName);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)" +
            " AND p.active = 1")
    Page<Product> searchProducts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)" +
            " AND p.active = 1")
    List<Product> searchProductsForCounter(@Param("keyword") String keyword);


    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
    Optional<Product> getDetailsProduct(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);

    int countAllBy();

    //@Query("SELECT p FROM Product p WHERE p.quantity = 0")
    int countAllByQuantity(Long quantity);

    @Query("SELECT p FROM Product p JOIN OrderDetail od ON p.id = od.product.id " +
            "GROUP BY p.id ORDER BY COUNT(od.id) DESC")
    List<Product> findTop4Products(Pageable pageable);
    List<Product> findAllByQuantity(Long quantity);
}
