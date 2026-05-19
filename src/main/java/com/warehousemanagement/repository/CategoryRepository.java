package com.warehousemanagement.repository;

import com.warehousemanagement.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

  boolean existsByNameIgnoreCase(String name);
}
