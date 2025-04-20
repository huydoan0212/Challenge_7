package com.example.challenge_7.controller;


import com.example.challenge_7.dto.request.UserCreationRequest;
import com.example.challenge_7.dto.request.UserUpdateRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.UserResponse;
import com.example.challenge_7.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {
    UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id) {
        UserResponse response = userService.getUserById(id);
        return ApiResponse.<UserResponse>builder()
                .message("Get user by id: " + id)
                .responseData(response)
                .status(response != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        UserResponse response = userService.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .message("Create user")
                .violation(null)
                .status(response != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .responseData(response)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ApiResponse.<UserResponse>builder()
                .message("Update user")
                .violation(null)
                .status(response != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .responseData(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable String id) {
        UserResponse response = userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder()
                .message("Deleted user")
                .violation(null)
                .status(response != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .responseData(response)
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        UserResponse response = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder()
                .message("My info")
                .violation(null)
                .status(response != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .responseData(response)
                .build();
    }

    @GetMapping
    public ApiResponse<CustomPage<UserResponse>> getAllUsers(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @RequestParam(required = false) String firstName,
                                                             @RequestParam(required = false) String lastName,
                                                             @RequestParam(required = false) String username,
                                                             @RequestParam(required = false) String sortBy) {
        CustomPage<UserResponse> users = userService.getAllUsers(pageable, username, firstName, lastName, sortBy);

        return ApiResponse.<CustomPage<UserResponse>>builder()
                .message("Get users")
                .status("success")
                .timeStamp(LocalDateTime.now())
                .responseData(users)
                .build();
    }


}
