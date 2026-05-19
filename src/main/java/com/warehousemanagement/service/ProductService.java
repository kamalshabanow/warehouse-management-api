package com.warehousemanagement.service;

import com.warehousemanagement.dto.request.CreateProductRequest;
import com.warehousemanagement.dto.request.UpdateProductRequest;
import com.warehousemanagement.dto.response.ProductResponse;
import com.warehousemanagement.entity.CategoryEntity;
import com.warehousemanagement.entity.ProductEntity;
import com.warehousemanagement.exception.ResourceNotFoundException;
import com.warehousemanagement.repository.CategoryRepository;
import com.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductMapper productMapper;

  @Transactional
  public ProductResponse create(CreateProductRequest request) {
    CategoryEntity category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    ProductEntity product = ProductEntity.builder()
        .name(request.getName().trim())
        .price(request.getPrice())
        .minStock(request.getMinStock())
        .stockQuantity(0)
        .supplierName(request.getSupplierName().trim())
        .category(category)
        .build();

    return productMapper.toResponse(productRepository.save(product));
  }

  @Transactional
  public List<ProductResponse> getAll() {
    return productRepository.findAll()
        .stream()
        .map(productMapper::toResponse)
        .toList();
  }

  @Transactional
  public ProductResponse update(UUID id, UpdateProductRequest request) {
    ProductEntity product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    CategoryEntity category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    product.setName(request.getName());
    product.setPrice(request.getPrice());
    product.setMinStock(request.getMinStock());
    product.setSupplierName(request.getSupplierName());
    product.setCategory(category);

    return productMapper.toResponse(product);
  }
}
