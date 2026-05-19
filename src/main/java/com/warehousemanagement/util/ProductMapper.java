package com.warehousemanagement.util;

import com.warehousemanagement.dto.response.ProductResponse;
import com.warehousemanagement.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductResponse toResponse(ProductEntity product) {
    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getMinStock(),
        product.getStockQuantity(),
        product.getSupplierName(),
        product.getCategory().getId(),
        product.getCategory().getName(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }
}