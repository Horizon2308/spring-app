package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR c.fullName LIKE %:keyword% OR c.phoneNumber LIKE %:keyword%)")  // Added condition to exclude role.id = 2
    List<Customer> searchCustomers(
            @Param("keyword") String keyword);
}
