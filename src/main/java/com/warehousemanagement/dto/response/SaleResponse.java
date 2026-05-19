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
public class SaleResponse {

  private UUID id;
  private UUID productId;
  private String productName;
  private Integer quantity;
  private BigDecimal priceAtSale;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
