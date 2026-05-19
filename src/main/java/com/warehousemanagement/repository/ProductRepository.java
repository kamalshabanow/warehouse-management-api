package com.warehousemanagement.repository;

import com.warehousemanagement.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
      SELECT p
      FROM ProductEntity p
      WHERE p.id = :id
      """)
  Optional<ProductEntity> findByIdForUpdate(@Param("id") UUID id);
}
