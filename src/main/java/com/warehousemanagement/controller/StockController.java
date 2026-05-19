package com.warehousemanagement.controller;

import com.warehousemanagement.dto.request.StockInRequest;
import com.warehousemanagement.dto.response.ProductResponse;
import com.warehousemanagement.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stock")
@RequiredArgsConstructor
public class StockController {

  private final StockService stockService;

  @PostMapping("/in")
  public ResponseEntity<ProductResponse> stockIn(@Valid @RequestBody StockInRequest request) {
    return ResponseEntity.ok(stockService.stockIn(request));
  }
}
