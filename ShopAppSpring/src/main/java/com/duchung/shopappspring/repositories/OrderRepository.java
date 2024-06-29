package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Order;
import com.duchung.shopappspring.models.User;
import com.duchung.shopappspring.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);
    int countAllBy();
    List<Order> findTop4ByOrderByIdDesc();

    @Query("SELECT o FROM Order o WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR o.fullName LIKE %:keyword% OR o.phoneNumber LIKE %:keyword%)" +
            " AND o.active = 1")
    Page<Order> searchOrder(
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query("SELECT SUM(COALESCE(o.totalMoney, 0)) FROM Order o WHERE o.status = 'success'")
    Double findTotalMoneySum();
    int countByStatus(String status);
    List<Order> findByStatus(String status);
}
