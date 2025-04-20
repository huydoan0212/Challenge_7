package com.example.challenge_7.services;


import com.example.challenge_7.dto.request.ProductRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String id, ProductRequest request);

    void deleteProduct(String id);

    CustomPage<ProductResponse> getProducts(Pageable pageable, String name, String brand, String category, String type);

    ProductResponse getProduct(String id);
}
