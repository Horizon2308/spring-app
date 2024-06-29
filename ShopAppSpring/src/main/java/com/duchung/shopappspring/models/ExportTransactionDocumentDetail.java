package com.duchung.shopappspring.models;

import com.duchung.shopappspring.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "export_transaction_document_details")
@Entity
@Builder
public class ExportTransactionDocumentDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "raw_product_name")
    private String rawProductName;

    @Column(nullable = false)
    private double price;

    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne (cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_document_id")
    private TransactionDocument transactionDocument;
}
