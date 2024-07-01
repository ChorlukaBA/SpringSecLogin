package com.lm.SpringSecLogin.repository;

import com.lm.SpringSecLogin.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
}