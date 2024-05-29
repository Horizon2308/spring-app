package com.duchung.shopappspring.repositories;

import com.duchung.shopappspring.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsRoleByName(String name);
}
