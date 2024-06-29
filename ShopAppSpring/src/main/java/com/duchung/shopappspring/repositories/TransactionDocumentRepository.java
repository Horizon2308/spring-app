package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.TransactionDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionDocumentRepository extends JpaRepository<TransactionDocument, Long> {
    @Query("SELECT td FROM TransactionDocument td WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR td.name LIKE %:keyword%) AND " +
            "(:type IS NULL OR :type = '' OR td.type = :type)")
    Page<TransactionDocument> searchTransactionDocuments(
            @Param("keyword") String keyword,
            @Param("type") String type,
            Pageable pageable);
}
