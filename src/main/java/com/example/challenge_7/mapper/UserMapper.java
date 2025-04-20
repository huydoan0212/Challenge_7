package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.UserCreationRequest;
import com.example.challenge_7.dto.request.UserUpdateRequest;
import com.example.challenge_7.dto.response.UserResponse;
import com.example.challenge_7.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    UserResponse toUserResponse(User user);
}

