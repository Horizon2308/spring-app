package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Order;
import com.duchung.shopappspring.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrder(Order order);
}
