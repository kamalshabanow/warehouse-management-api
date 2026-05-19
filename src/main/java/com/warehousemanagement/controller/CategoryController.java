package com.warehousemanagement.controller;

import com.warehousemanagement.dto.request.CreateCategoryRequest;
import com.warehousemanagement.dto.response.CategoryResponse;
import com.warehousemanagement.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryResponse> create(
      @Valid @RequestBody CreateCategoryRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getAll() {
    return ResponseEntity.ok(categoryService.getAll());
  }
}
