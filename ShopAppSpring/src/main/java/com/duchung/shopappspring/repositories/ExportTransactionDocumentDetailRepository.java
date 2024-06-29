package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.ExportTransactionDocumentDetail;
import com.duchung.shopappspring.models.TransactionDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportTransactionDocumentDetailRepository extends JpaRepository<ExportTransactionDocumentDetail, Long> {
    List<ExportTransactionDocumentDetail> findAllByTransactionDocument(TransactionDocument transactionDocument);
}
