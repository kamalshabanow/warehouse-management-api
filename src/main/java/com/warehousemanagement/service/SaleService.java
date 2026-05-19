package com.warehousemanagement.service;

import com.warehousemanagement.dto.request.CreateSaleRequest;
import com.warehousemanagement.dto.response.SaleResponse;
import com.warehousemanagement.entity.ProductEntity;
import com.warehousemanagement.entity.SaleEntity;
import com.warehousemanagement.exception.BadRequestException;
import com.warehousemanagement.exception.ResourceNotFoundException;
import com.warehousemanagement.repository.ProductRepository;
import com.warehousemanagement.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaleService {

  private final SaleRepository saleRepository;
  private final ProductRepository productRepository;

  @Transactional
  public SaleResponse create(CreateSaleRequest request) {
    ProductEntity product = productRepository.findByIdForUpdate(request.getProductId())
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    if (product.getStockQuantity() < request.getQuantity()) {
      throw new BadRequestException("Insufficient stock");
    }

    product.setStockQuantity(product.getStockQuantity() - request.getQuantity());

    SaleEntity sale = SaleEntity.builder()
        .product(product)
        .quantity(request.getQuantity())
        .priceAtSale(product.getPrice())
        .build();

    SaleEntity savedSale = saleRepository.save(sale);

    return new SaleResponse(
        savedSale.getId(),
        product.getId(),
        product.getName(),
        savedSale.getQuantity(),
        savedSale.getPriceAtSale(),
        savedSale.getCreatedAt(),
        savedSale.getUpdatedAt()
    );
  }
}
