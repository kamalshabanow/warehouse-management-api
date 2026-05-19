package com.warehousemanagement.controller;

import com.warehousemanagement.dto.request.CreateSaleRequest;
import com.warehousemanagement.dto.response.SaleResponse;
import com.warehousemanagement.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/sales")
@RequiredArgsConstructor
public class SaleController {

  private final SaleService saleService;

  @PostMapping
  public ResponseEntity<SaleResponse> create(
      @Valid @RequestBody CreateSaleRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(request));
  }
}
