package com.warehousemanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSaleRequest {

  @NotNull(message = "Product id is required")
  private UUID productId;

  @NotNull(message = "Quantity is required")
  @Positive(message = "Quantity must be positive")
  private Integer quantity;
}
