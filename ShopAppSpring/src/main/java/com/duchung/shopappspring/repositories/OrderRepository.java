package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Order;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
}
