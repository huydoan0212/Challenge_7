package com.example.challenge_7.services.impl;


import com.example.challenge_7.dto.request.UserCreationRequest;
import com.example.challenge_7.dto.request.UserUpdateRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.UserResponse;
import com.example.challenge_7.entity.Role;
import com.example.challenge_7.entity.User;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.mapper.UserMapper;
import com.example.challenge_7.repo.CartRepository;
import com.example.challenge_7.repo.RoleRepository;
import com.example.challenge_7.repo.UserRepository;
import com.example.challenge_7.repo.specification.UserSpecifications;
import com.example.challenge_7.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    CartRepository cartRepository;


    //    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public CustomPage<UserResponse> getAllUsers(Pageable pageable, String username, String firstName, String lastName, String sortBy) {
        // Xác định cách phân trang và sắp xếp
        if (pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }

        // Xác định các tiêu chí tìm kiếm
        Specification<User> spec = Specification.where(null);

        if (StringUtils.hasText(username)) {
            spec = spec.and(UserSpecifications.likeUsername(username));
        }

        if (StringUtils.hasText(firstName)) {
            spec = spec.and(UserSpecifications.likeFirstName(firstName));
        }

        if (StringUtils.hasText(lastName)) {
            spec = spec.and(UserSpecifications.likeLastName(lastName));
        }

        Page<User> usersPage = userRepository.findAll(spec, pageable);

        CustomPage<UserResponse> customPage = PageMapper.toCustomPage(usersPage.map(userMapper::toUserResponse));

        return customPage;
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));
        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));
        if (user != null) {
            userRepository.delete(user);
        }
        assert user != null;
        return userMapper.toUserResponse(user);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(Error.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        Role role = roleRepository.findById("USER").orElseThrow(() -> new CustomException(Error.ROLE_NOT_FOUND));
        roles.add(role);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        if (context == null) {
            throw new CustomException(Error.UNAUTHENTICATED);
        }
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new CustomException(Error.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


}
