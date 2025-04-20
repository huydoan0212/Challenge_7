package com.example.challenge_7.services;


import com.example.challenge_7.dto.request.UserCreationRequest;
import com.example.challenge_7.dto.request.UserUpdateRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse getUserById(String id);

    CustomPage<UserResponse> getAllUsers(Pageable pageable, String username, String firstName, String lastName, String sortBy);

    UserResponse updateUser(String id, UserUpdateRequest request);

    UserResponse deleteUser(String id);

    UserResponse createUser(UserCreationRequest request);

    UserResponse getMyInfo();


//    CustomPage<?> advanceSearchWithSpecifications(Pageable pageable, String[] user, String[] address);
}
