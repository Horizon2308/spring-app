package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsUserByPhoneNumber(String phoneNumber);
}
