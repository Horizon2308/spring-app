package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.RoleDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.models.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role createRole(RoleDTO roleDTO) throws DataExistedException;
}
