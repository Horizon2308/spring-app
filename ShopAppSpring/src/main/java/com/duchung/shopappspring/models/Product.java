package com.duchung.shopappspring.models;

import com.duchung.shopappspring.domains.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 350)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(length = 300)
    private String thumbnail;

    private String description;

    @Column(name = "active")
    @JsonProperty("is_active")
    private int isActive;

    @OneToMany(mappedBy = "product",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
