package com.duchung.shopappspring.models;


import com.duchung.shopappspring.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "raw_products")
@Entity
@Builder
public class RawProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Long quantity;

    @Column
    private String note;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
}
