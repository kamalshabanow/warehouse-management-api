package com.warehousemanagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

  @NotBlank(message = "Product name is required")
  private String name;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
  private BigDecimal price;

  @NotNull(message = "Minimum stock is required")
  @Min(value = 0, message = "Minimum stock cannot be negative")
  private Integer minStock;

  @NotBlank(message = "Supplier name is required")
  private String supplierName;

  @NotNull(message = "Category id is required")
  private UUID categoryId;
}
