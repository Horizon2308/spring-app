package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsUserByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR u.fullName LIKE %:keyword% OR u.phoneNumber LIKE %:keyword%)" +
            " AND u.isActive = 1" +
            " AND u.role.id <> 2")  // Added condition to exclude role.id = 2
    Page<User> searchStaffs(
            @Param("keyword") String keyword,
            Pageable pageable);
}
