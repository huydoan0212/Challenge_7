package com.example.challenge_7.mapper;

import com.example.challenge_7.dto.request.ProductRequest;
import com.example.challenge_7.dto.response.ProductResponse;

import com.example.challenge_7.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product role);

    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
