package com.duchung.shopappspring.services;

import com.duchung.shopappspring.dtos.RoleDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(RoleDTO roleDTO) throws DataExistedException {
        if (roleRepository.existsRoleByName(roleDTO.getName())) {
            throw new DataExistedException("Chức vụ đã tồn tại");
        }
        return roleRepository.save(Role.builder()
                        .name(roleDTO.getName())
                .build());
    }
}
