package com.prodcate.prodcate.repositories;

import com.prodcate.prodcate.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :id")
    Optional<Category> findByIdWithProducts(@Param("id") UUID id);
}
