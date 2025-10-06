package com.prodcate.prodcate.repositories;

import com.prodcate.prodcate.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByCategoryId(UUID categoryId);
}
