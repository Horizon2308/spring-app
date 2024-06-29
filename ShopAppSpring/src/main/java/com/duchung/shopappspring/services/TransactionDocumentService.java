package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.ExportRawProductDTO;
import com.duchung.shopappspring.dtos.ExportTransactionDocumentDTO;
import com.duchung.shopappspring.dtos.RawProductDTO;
import com.duchung.shopappspring.dtos.TransactionDocumentDTO;
import com.duchung.shopappspring.exceptions.DataNotFoundException;
import com.duchung.shopappspring.models.*;
import com.duchung.shopappspring.repositories.*;
import com.duchung.shopappspring.responses.ExportTransactionDocumentDetailResponse;
import com.duchung.shopappspring.responses.TransactionDocumentDetailsResponse;
import com.duchung.shopappspring.responses.TransactionDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionDocumentService implements ITransactionDocumentService {

    private final TransactionDocumentRepository transactionDocumentRepository;
    private final TransactionDocumentDetailRepository transactionDocumentDetailRepository;
    private final RawProductRepository rawProductRepository;
    private final RawProductService rawProductService;
    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;
    private final StoreRepository storeRepository;
    private final ExportTransactionDocumentDetailRepository exportTransactionDocumentDetailRepository;

    @Override
    public Page<TransactionDocumentResponse> getAllTransactionDocuments(Integer sortOption,
                                                                        String keyword,
                                                                        Integer page,
                                                                        Integer limit,
                                                                        String type) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        switch (sortOption) {
            case 1:
                sort = Sort.by(Sort.Direction.DESC, "createAt");
                break;
            case 2:
                sort = Sort.by(Sort.Direction.ASC, "id");
                break;
            case 3:
                sort = Sort.by(Sort.Direction.ASC, "totalProducts");
                break;
        }
        return transactionDocumentRepository
                .searchTransactionDocuments(keyword, type, PageRequest.of(page, limit, sort))
                .map(this::convertToTransactionDocumentResponse);
    }

    @Transactional
    @Override
    public void createTransactionDocument(TransactionDocumentDTO transactionDocumentDTO, String type) throws DataNotFoundException {
        User user = userRepository.findById(transactionDocumentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        TransactionDocument transactionDocument = TransactionDocument.builder()
                .name(transactionDocumentDTO.getName())
                .user(user)
                .totalProducts(transactionDocumentDTO.getRawProducts().size())
                .type(type)
                .build();
        TransactionDocument savedTransactionDocument = transactionDocumentRepository.save(transactionDocument);
        List<RawProduct> rawProducts = transactionDocumentDTO.getRawProducts().stream()
                .map(rawProductDTO -> {
                    Provider provider = providerRepository.findById(rawProductDTO.getProviderId()).get();
                    return rawProductService.convertToRawProduct(rawProductDTO, provider);
                }).toList();
        rawProductRepository.saveAll(rawProducts);
        List<TransactionDocumentDetail> transactionDocumentDetails = transactionDocumentDTO.getRawProducts().stream()
                .map(rawProductDTO -> {
                    Provider provider = providerRepository.findById(rawProductDTO.getProviderId()).get();
                    return this.convertToTransactionDocumentDetail(rawProductDTO, provider, savedTransactionDocument);
                }).toList();
        transactionDocumentDetailRepository.saveAll(transactionDocumentDetails);
    }

    @Override
    public void deleteTransactionDocument(Long transactionDocumentId) throws DataNotFoundException {
        TransactionDocument deletedtransactionDocument = transactionDocumentRepository.findById(transactionDocumentId)
                .orElseThrow(() -> new DataNotFoundException("Transaction document not found!"));
        transactionDocumentRepository.delete(deletedtransactionDocument);
    }

    @Override
    public List<TransactionDocumentDetailsResponse> getTransactionDocumentDetailsByTDId(Long transactionDocumentId)
            throws DataNotFoundException {
        TransactionDocument transactionDocument = transactionDocumentRepository.findById(transactionDocumentId)
                .orElseThrow(() -> new DataNotFoundException("Transaction document not found!"));
        return transactionDocumentDetailRepository.findAllByTransactionDocument(transactionDocument).stream()
                .map(this::convertToTransactionDocumentDetailsResponse)
                .toList();
    }

    @Override
    public void createExportTransactionDocument(ExportTransactionDocumentDTO exportTransactionDocumentDTO) throws DataNotFoundException {
        User user = userRepository.findById(exportTransactionDocumentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found!"));
        TransactionDocument transactionDocument = TransactionDocument.builder()
                .name(exportTransactionDocumentDTO.getName())
                .user(user)
                .totalProducts(exportTransactionDocumentDTO.getRawProducts().size())
                .type("export")
                .build();
        TransactionDocument savedTransactionDocument = transactionDocumentRepository.save(transactionDocument);
        List<ExportTransactionDocumentDetail> transactionDocumentDetails = exportTransactionDocumentDTO.getRawProducts().stream()
                .map(rawProductDTO -> {
                    Store store = storeRepository.findById(rawProductDTO.getStoreId()).get();
                    return this.convertToExportTransactionDocumentDetail(rawProductDTO,
                            store,
                            savedTransactionDocument);
                }).toList();
        exportTransactionDocumentDetailRepository.saveAll(transactionDocumentDetails);
        exportTransactionDocumentDTO.getRawProducts().forEach(item -> {
            RawProduct rawProduct = rawProductRepository.findById(item.getId()).get();
            rawProduct.setQuantity(rawProduct.getQuantity() - item.getQuantity());
            rawProductRepository.save(rawProduct);
        });
    }

    @Override
    public List<ExportTransactionDocumentDetailResponse> getExportTransactionDocumentDetailsByTDId(Long transactionDocumentId) throws DataNotFoundException {
        TransactionDocument transactionDocument = transactionDocumentRepository.findById(transactionDocumentId)
                .orElseThrow(() -> new DataNotFoundException("Transaction document not found!"));
        return exportTransactionDocumentDetailRepository.findAllByTransactionDocument(transactionDocument).stream()
                .map(this::convertToTransactionDocumentDetailsResponse)
                .toList();
    }

    private TransactionDocumentDetail convertToTransactionDocumentDetail(RawProductDTO rawProductDTO,
                                                                         Provider provider,
                                                                         TransactionDocument transactionDocument) {
        return TransactionDocumentDetail.builder()
                .price(rawProductDTO.getPrice())
                .quantity(rawProductDTO.getQuantity())
                .provider(provider)
                .transactionDocument(transactionDocument)
                .rawProductName(rawProductDTO.getName())
                .build();
    }

    private ExportTransactionDocumentDetail convertToExportTransactionDocumentDetail(ExportRawProductDTO rawProductDTO,
                                                                                     Store store,
                                                                                     TransactionDocument transactionDocument) {
        return ExportTransactionDocumentDetail.builder()
                .store(store)
                .price(rawProductDTO.getPrice())
                .quantity(rawProductDTO.getQuantity())
                .rawProductName(rawProductDTO.getName())
                .transactionDocument(transactionDocument)
                .build();
    }

    private TransactionDocumentResponse convertToTransactionDocumentResponse(TransactionDocument transactionDocument) {
        return TransactionDocumentResponse.builder()
                .id(transactionDocument.getId())
                .username(transactionDocument.getUser().getFullName())
                .type(transactionDocument.getType())
                .name(transactionDocument.getName())
                .totalProducts(transactionDocument.getTotalProducts())
                .createdAt(transactionDocument.getCreateAt())
                .build();
    }

    private TransactionDocumentDetailsResponse convertToTransactionDocumentDetailsResponse(
            TransactionDocumentDetail transactionDocumentDetail) {
        return TransactionDocumentDetailsResponse.builder()
                .providerName(transactionDocumentDetail.getProvider().getName())
                .id(transactionDocumentDetail.getId())
                .price(transactionDocumentDetail.getPrice())
                .rawProductName(transactionDocumentDetail.getRawProductName())
                .quantity(transactionDocumentDetail.getQuantity())
                .createdAt(transactionDocumentDetail.getCreateAt())
                .build();
    }

    private ExportTransactionDocumentDetailResponse convertToTransactionDocumentDetailsResponse(
            ExportTransactionDocumentDetail exportTransactionDocumentDetail) {
        return ExportTransactionDocumentDetailResponse.builder()
                .storeName(exportTransactionDocumentDetail.getStore().getName())
                .id(exportTransactionDocumentDetail.getId())
                .price(exportTransactionDocumentDetail.getPrice())
                .rawProductName(exportTransactionDocumentDetail.getRawProductName())
                .quantity(exportTransactionDocumentDetail.getQuantity())
                .createdAt(exportTransactionDocumentDetail.getCreateAt())
                .build();
    }
}