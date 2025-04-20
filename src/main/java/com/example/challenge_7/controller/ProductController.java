package com.example.challenge_7.controller;


import com.example.challenge_7.dto.request.ProductRequest;
import com.example.challenge_7.dto.response.ApiResponse;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ProductResponse;
import com.example.challenge_7.services.impl.ProductServiceImpl;
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
@RequestMapping("/api/products")
@Tag(name = "Product")
public class ProductController {
    ProductServiceImpl productServiceImpl;

    @GetMapping
    public ApiResponse<CustomPage<ProductResponse>> getProducts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String brand,
                                                                @RequestParam(required = false) String category,
                                                                @RequestParam(required = false) String type) {
        CustomPage<ProductResponse> products = productServiceImpl.getProducts(pageable, name, brand, category, type);
        return ApiResponse.<CustomPage<ProductResponse>>builder()
                .message("Get all products")
                .responseData(products)
                .status(products != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable("id") String id) {
        ProductResponse productResponse = productServiceImpl.getProduct(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Get product")
                .responseData(productResponse)
                .status(productResponse != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();
    }

    @PostMapping
    @ResponseBody
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productServiceImpl.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .message("Create product")
                .responseData(productResponse)
                .status(productResponse != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable("id") String id, @RequestBody ProductRequest request) {
        ProductResponse productResponse = productServiceImpl.updateProduct(id, request);
        return ApiResponse.<ProductResponse>builder()
                .message("Update product")
                .responseData(productResponse)
                .status(productResponse != null ? "success" : "failure")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();

    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") String id) {
        productServiceImpl.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("Delete product")
                .status("success")
                .timeStamp(LocalDateTime.now())
                .violation(null)
                .build();
    }

}
