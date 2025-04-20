package com.example.challenge_7.services.impl;


import com.example.challenge_7.dto.request.RoleRequest;
import com.example.challenge_7.dto.response.RoleResponse;
import com.example.challenge_7.entity.Role;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.RoleMapper;
import com.example.challenge_7.repo.RoleRepository;
import com.example.challenge_7.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getRoles() {
        List<RoleResponse> roles = new ArrayList<>();
        roleRepository.findAll().forEach(role -> {
            roles.add(roleMapper.toRoleResponse(role));
        });
        return roles;
    }

    @Override
    public RoleResponse addRole(RoleRequest roleRequest) {
        if (roleRepository.existsById(roleRequest.getName())) {
            throw new CustomException(Error.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(roleRequest);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(String id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new CustomException(Error.ROLE_NOT_FOUND));
        role.setName(roleRequest.getName());
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
