package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR s.name LIKE CONCAT('%', :keyword, '%') OR s.phoneNumber LIKE CONCAT('%', :keyword, '%'))")
    Page<Store> searchStores(
            @Param("keyword") String keyword,
            Pageable pageable);
}
