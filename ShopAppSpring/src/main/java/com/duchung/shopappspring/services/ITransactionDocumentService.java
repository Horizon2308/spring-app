package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ExportTransactionDocumentDTO;
import com.duchung.shopappspring.dtos.TransactionDocumentDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.TransactionDocumentDetail;
import com.duchung.shopappspring.responses.ExportTransactionDocumentDetailResponse;
import com.duchung.shopappspring.responses.TransactionDocumentDetailsResponse;
import com.duchung.shopappspring.responses.TransactionDocumentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionDocumentService {
    Page<TransactionDocumentResponse> getAllTransactionDocuments(Integer sortOption,
                                                                 String keyword,
                                                                 Integer page,
                                                                 Integer limit,
                                                                 String type);
    void createTransactionDocument(TransactionDocumentDTO transactionDocumentDTO, String type) throws DataNotFoundException;
    void deleteTransactionDocument(Long transactionDocumentId) throws DataNotFoundException;
    List<TransactionDocumentDetailsResponse> getTransactionDocumentDetailsByTDId(Long transactionDocumentId) throws DataNotFoundException;
    void createExportTransactionDocument(ExportTransactionDocumentDTO exportTransactionDocumentDTO) throws DataNotFoundException;
    List<ExportTransactionDocumentDetailResponse> getExportTransactionDocumentDetailsByTDId(Long transactionDocumentId) throws DataNotFoundException;
}
