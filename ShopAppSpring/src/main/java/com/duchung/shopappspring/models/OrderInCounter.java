package com.duchung.shopappspring.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders_in_counter")
@Entity
@Builder
public class OrderInCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User user;

    @Column(length = 200)
    private String address;

    @Column(length = 100)
    private String note;

    private LocalDate orderDate;

    private double totalMoney;

    @Column(length = 100)
    private String paymentMethod;

}
