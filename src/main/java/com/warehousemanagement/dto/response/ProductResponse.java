package com.warehousemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private UUID id;
  private String name;
  private BigDecimal price;
  private Integer minStock;
  private Integer stockQuantity;
  private String supplierName;
  private UUID categoryId;
  private String categoryName;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
