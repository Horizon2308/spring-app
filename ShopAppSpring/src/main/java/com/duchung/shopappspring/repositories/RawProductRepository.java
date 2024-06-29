package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Product;
import com.duchung.shopappspring.models.RawProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RawProductRepository extends JpaRepository<RawProduct, Long> {
    @Query("SELECT rp FROM RawProduct rp WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR rp.name LIKE CONCAT('%', :keyword, '%') OR rp.note LIKE CONCAT('%', :keyword, '%'))")
    Page<RawProduct> searchRawProducts(
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("SELECT rp FROM RawProduct rp WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR rp.name LIKE %:keyword% OR rp.note LIKE %:keyword%)")
    List<RawProduct> searchRawProducts(@Param("keyword") String keyword);
}
