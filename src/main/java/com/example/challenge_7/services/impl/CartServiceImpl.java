package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.request.CartCreationRequest;
import com.example.challenge_7.dto.response.CartResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.entity.Cart;
import com.example.challenge_7.entity.User;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.CartMapper;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.mapper.UserMapper;
import com.example.challenge_7.repo.CartRepository;
import com.example.challenge_7.repo.UserRepository;
import com.example.challenge_7.services.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    UserRepository userRepository;
    CartMapper cartMapper;
    UserMapper userMapper;

    @Override
    public CartResponse createCart(CartCreationRequest cartCreationRequest) {
        User user = userRepository.findById(cartCreationRequest.getUserId()).orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));
        Cart cart = cartRepository.save(Cart.builder()
                .user(user)
                .build());
        return CartResponse.builder()
                .userResponse(userMapper.toUserResponse(user))
                .id(cart.getId())
                .build();
    }

    @Override
    public CustomPage<CartResponse> getCart(Pageable pageable) {
        Page<Cart> carts = cartRepository.findAll(pageable);
        return PageMapper.toCustomPage(carts.map(cartMapper::toCartResponse));
    }
}
