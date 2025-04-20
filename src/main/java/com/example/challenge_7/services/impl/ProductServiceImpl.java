package com.example.challenge_7.services.impl;


import com.example.challenge_7.dto.request.ProductRequest;
import com.example.challenge_7.dto.response.CustomPage;
import com.example.challenge_7.dto.response.ProductResponse;
import com.example.challenge_7.entity.Product;
import com.example.challenge_7.exception.CustomException;
import com.example.challenge_7.exception.Error;
import com.example.challenge_7.mapper.PageMapper;
import com.example.challenge_7.mapper.ProductMapper;
import com.example.challenge_7.repo.ProductRepository;
import com.example.challenge_7.repo.specification.ProductSpecifications;
import com.example.challenge_7.services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

//    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductRequest request) {
        productRepository.save(productMapper.toProduct(request));
        return productMapper.toProductResponse(productMapper.toProduct(request));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND));
        productMapper.updateProduct(request, product);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public CustomPage<ProductResponse> getProducts(Pageable pageable, String name, String brand, String category, String type) {
        Specification<Product> spec = Specification.where(null);

        if (pageable.getSort().isEmpty()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }

        if (StringUtils.hasText(name)) {
            spec = spec.or(ProductSpecifications.likeName(name));
        }
        if (StringUtils.hasText(brand)) {
            spec = spec.or(ProductSpecifications.likeBrand(brand));
        }
        if (StringUtils.hasText(category)) {
            spec = spec.or(ProductSpecifications.likeCategory(category));
        }
        if (StringUtils.hasText(type)) {
            spec = spec.or(ProductSpecifications.likeType(type));
        }

        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        CustomPage<ProductResponse> customPage = PageMapper.toCustomPage(productsPage.map(productMapper::toProductResponse));

        return customPage;
    }


//    @PreAuthorize("hasRole('USER')")
    public ProductResponse getProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(Error.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }

}
