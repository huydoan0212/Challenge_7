package com.example.challenge_7.services.impl;

import com.example.challenge_7.dto.request.CartItemRequest;
import com.example.challenge_7.dto.response.CartItemResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.entity.CartItem;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.CartItemMapper;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.repo.CartItemRepository;
import com.example.challenge_7.repo.CartRepository;
import com.example.challenge_7.repo.ProductRepository;
import com.example.challenge_7.repo.specification.CartItemSpecifications;
import com.example.challenge_7.services.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    CartRepository cartRepository;
    CartItemMapper cartItemMapper;

    @Override
    public CartItemResponse createCartItem(CartItemRequest cartItemRequest) {
        boolean checkCartItem = checkCartIdAndProductId(cartItemRequest.getCartId(), cartItemRequest.getProductId());
        CartItem cartItem;
        if (!checkCartItem) {
            cartItem = cartItemRepository.save(CartItem.builder()
                    .product(productRepository.findById(cartItemRequest.getProductId()).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND)))
                    .quantity(cartItemRequest.getQuantity())
                    .cart(cartRepository.findById(cartItemRequest.getCartId()).orElseThrow(() -> new CustomException(Error.CART_NOT_FOUND)))
                    .build());
        } else {
            Specification<CartItem> spec = Specification.where(null);
            spec = spec.and(CartItemSpecifications.likeCartId(cartItemRequest.getCartId()))
                    .and(CartItemSpecifications.likeProductId(cartItemRequest.getProductId()));
            List<CartItem> cartItems = cartItemRepository.findAll(spec);
            if (!cartItems.isEmpty()) {
                cartItem = cartItems.get(0);
                int quantity = cartItemRequest.getQuantity() + cartItem.getQuantity();
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
            } else {
                throw new CustomException(Error.CART_ITEM_NOT_FOUND);
            }
        }
        return cartItemMapper.toCartItemResponse(cartItem);
    }

    @Override
    public CustomPage<CartItemResponse> getCartItems(Pageable pageable, String cartId, String productId) {
        // Kiểm tra và thiết lập giá trị mặc định cho pageable nếu null hoặc không có sort
        if (pageable == null || pageable.getSort().isEmpty()) {
            int pageNumber = pageable != null ? pageable.getPageNumber() : 0;
            int pageSize = pageable != null ? pageable.getPageSize() : 10;
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        // Tạo Specification với các điều kiện tìm kiếm
        Specification<CartItem> spec = Specification.where(null);

        if (StringUtils.hasText(cartId)) {
            spec = spec.and(CartItemSpecifications.likeCartId(cartId));
        }

        if (StringUtils.hasText(productId)) {
            spec = spec.and(CartItemSpecifications.likeProductId(productId));
        }

        // Truy vấn các CartItem theo điều kiện và phân trang
        Page<CartItem> cartItems = cartItemRepository.findAll(spec, pageable);

        // Chuyển đổi kết quả sang CustomPage<CartItemResponse>
        return PageMapper.toCustomPage(cartItems.map(cartItemMapper::toCartItemResponse));
    }


    @Override
    public boolean deleteCartItem(String cartId, String productId) {
        // Kiểm tra nếu cả hai điều kiện đều rỗng thì trả về false
        if (!StringUtils.hasText(cartId) && !StringUtils.hasText(productId)) {
            return false;
        }

        Specification<CartItem> spec = Specification.where(null);

        if (StringUtils.hasText(cartId)) {
            spec = spec.and(CartItemSpecifications.likeCartId(cartId));
        }

        if (StringUtils.hasText(productId)) {
            spec = spec.and(CartItemSpecifications.likeProductId(productId)); // Sử dụng 'and' thay vì 'or'
        }

        List<CartItem> cartItems = cartItemRepository.findAll(spec);
        if (!cartItems.isEmpty()) {
            cartItemRepository.deleteAll(cartItems);
            return true;
        }
        return false;
    }


    private boolean checkCartIdAndProductId(String cartId, String productId) {
        Specification<CartItem> spec = Specification.where(null);
        if (StringUtils.hasText(cartId) && StringUtils.hasText(productId)) {
            spec = spec.and(CartItemSpecifications.likeCartId(cartId))
                    .and(CartItemSpecifications.likeProductId(productId));
            return !cartItemRepository.findAll(spec).isEmpty();
        }
        return false;
    }
}
