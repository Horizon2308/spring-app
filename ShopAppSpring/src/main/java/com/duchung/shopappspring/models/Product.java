package com.duchung.shopappspring.models;

import com.duchung.shopappspring.domains.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private int isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
