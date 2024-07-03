package com.lm.SpringSecLogin.repository;

import com.lm.SpringSecLogin.model.Product;                                            // Product model class (Entity, POJO in JPA terminology)
import org.springframework.data.jpa.repository.JpaRepository;                          // Import of JpaRepository, which provides methods for CRUD operations

/*
    * ProductRepository interface extends JpaRepository, which provides CRUD methods for Product entity.
 */

public interface ProductRepository extends JpaRepository<Product, Long>
{
}