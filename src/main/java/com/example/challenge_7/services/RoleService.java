package com.example.challenge_7.services;


import com.example.challenge_7.dto.request.RoleRequest;
import com.example.challenge_7.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getRoles();

    RoleResponse addRole(RoleRequest roleRequest);

    RoleResponse updateRole(String id, RoleRequest roleRequest);

    void deleteRole(String name);
}
