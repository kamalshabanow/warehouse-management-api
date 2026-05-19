package com.warehousemanagement.controller;

import com.warehousemanagement.dto.request.CreateProductRequest;
import com.warehousemanagement.dto.request.UpdateProductRequest;
import com.warehousemanagement.dto.response.ProductResponse;
import com.warehousemanagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponse> create(
      @Valid @RequestBody CreateProductRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> getAll() {
    return ResponseEntity.ok(productService.getAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(
      @PathVariable UUID id,
      @RequestBody UpdateProductRequest request
  ) {
    return ResponseEntity.ok(productService.update(id, request));
  }
}
