package com.warehousemanagement.service;

import com.warehousemanagement.dto.request.CreateCategoryRequest;
import com.warehousemanagement.dto.response.CategoryResponse;
import com.warehousemanagement.entity.CategoryEntity;
import com.warehousemanagement.exception.BadRequestException;
import com.warehousemanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Transactional
  public CategoryResponse create(CreateCategoryRequest request) {
    if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
      throw new BadRequestException("Category already exists");
    }

    CategoryEntity savedCategory = categoryRepository.save(
        CategoryEntity.builder()
            .name(request.getName().trim())
            .build()
    );

    return toResponse(savedCategory);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponse> getAll() {
    return categoryRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
  }

  private CategoryResponse toResponse(CategoryEntity category) {
    return new CategoryResponse(category.getId(), category.getName());
  }
}
