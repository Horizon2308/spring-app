package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.ExportTransactionDocumentDTO;
import com.duchung.shopappspring.dtos.TransactionDocumentDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.http_responses.ErrorResponse;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.responses.TransactionDocumentListResponse;
import com.duchung.shopappspring.responses.TransactionDocumentResponse;
import com.duchung.shopappspring.services.ITransactionDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/manager/transaction-documents")
public class TransactionDocumentController {

    private final ITransactionDocumentService transactionDocumentService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/{type}")
    public ResponseEntity<?> getAllTransactionDocuments(@RequestParam(defaultValue = "1", value = "sort") int sortOption,
                                                        @RequestParam(defaultValue = "", value = "keyword") String keyword,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "12") Integer limit,
                                                        @PathVariable("type") String type) {
        Page<TransactionDocumentResponse> transactionDocuments = transactionDocumentService
                .getAllTransactionDocuments(sortOption, keyword, page, limit, type);
        int totalPage = transactionDocuments.getTotalPages();
        if (totalPage == 0) {
            return ResponseEntity.ok(new SuccessResponse<>("Transaction document page is empty!"));
        }
        if (transactionDocuments.getContent().isEmpty()) {
            return ResponseEntity.ok(new SuccessResponse<>("Raw products page is empty!"));
        }
        return ResponseEntity.ok(new SuccessResponse<>(TransactionDocumentListResponse.builder()
                .transactionDocuments(transactionDocuments)
                .totalPage(totalPage)
                .build()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/import")
    public ResponseEntity<?> createTransactionDocument(
            @RequestBody TransactionDocumentDTO transactionDocumentDTO) {
        try {
            String type = "import";
            transactionDocumentService.createTransactionDocument(transactionDocumentDTO, type);
            return ResponseEntity.ok(new SuccessResponse<>("Created successfully!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/export")
    public ResponseEntity<?> createExportTransactionDocument(
            @RequestBody ExportTransactionDocumentDTO exportTransactionDocumentDTO) {
        try {
            transactionDocumentService.createExportTransactionDocument(exportTransactionDocumentDTO);
            return ResponseEntity.ok(new SuccessResponse<>("Created successfully!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransactionDocument(@PathVariable("id") Long transactionDocumentId) {
        try {
            transactionDocumentService.deleteTransactionDocument(transactionDocumentId);
            return ResponseEntity.ok(new SuccessResponse<>("Deleted!"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getTransactionDocumentDetailsByTDId(@PathVariable("id") Long transactionDocumentId) {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(transactionDocumentService
                    .getTransactionDocumentDetailsByTDId(transactionDocumentId)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/export/details/{id}")
    public ResponseEntity<?> getExportTransactionDocumentDetailsByTDId(@PathVariable("id") Long transactionDocumentId) {
        try {
            return ResponseEntity.ok(new SuccessResponse<>(transactionDocumentService
                    .getExportTransactionDocumentDetailsByTDId(transactionDocumentId)));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
        }
    }
}
