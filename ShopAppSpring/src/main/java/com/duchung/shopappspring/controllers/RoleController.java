package com.duchung.shopappspring.controllers;

import com.duchung.shopappspring.dtos.RoleDTO;
import com.duchung.shopappspring.exceptions.DataExistedException;
import com.duchung.shopappspring.http_responses.SuccessResponse;
import com.duchung.shopappspring.models.Role;
import com.duchung.shopappspring.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<?> createRole(@RequestBody RoleDTO roleDTO) {
        try {
            roleService.createRole(roleDTO);
        } catch (DataExistedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(new SuccessResponse<>("Create successfully!"));
    }

}
