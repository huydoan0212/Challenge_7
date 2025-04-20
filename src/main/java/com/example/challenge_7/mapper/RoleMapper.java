package com.example.challenge_7.mapper;


import com.example.challenge_7.dto.request.RoleRequest;
import com.example.challenge_7.dto.response.RoleResponse;
import com.example.challenge_7.entity.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
