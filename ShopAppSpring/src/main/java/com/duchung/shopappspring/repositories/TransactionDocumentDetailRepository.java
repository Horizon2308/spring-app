package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.TransactionDocument;
import com.duchung.shopappspring.models.TransactionDocumentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDocumentDetailRepository extends JpaRepository<TransactionDocumentDetail, Long> {
    List<TransactionDocumentDetail> findAllByTransactionDocument(TransactionDocument transactionDocument);
}