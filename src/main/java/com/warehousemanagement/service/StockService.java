package com.warehousemanagement.service;

import com.warehousemanagement.dto.request.StockInRequest;
import com.warehousemanagement.dto.response.ProductResponse;
import com.warehousemanagement.entity.ProductEntity;
import com.warehousemanagement.exception.ResourceNotFoundException;
import com.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.util.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Transactional
  public ProductResponse stockIn(StockInRequest request) {
    ProductEntity product = productRepository.findByIdForUpdate(request.getProductId())
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    product.setStockQuantity(product.getStockQuantity() + request.getQuantity());

    return productMapper.toResponse(product);
  }
}
