package com.duchung.shopappspring.models;

import com.duchung.shopappspring.domains.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
@Builder
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(length = 100)
    private String email;

    @Column(length = 20, nullable = false, unique = true)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String note;

    private LocalDate orderDate;

    private String status;

    private double totalMoney;

    @Column(length = 100)
    private String shippingMethod;

    @Column(length = 200)
    private String shippingAddress;

    private LocalDate shippingDate;

    @Column(length = 100)
    private String trackingNumber;

    @Column(length = 100)
    private String paymentMethod;

    private int active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;

}
