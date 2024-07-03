package com.lm.SpringSecLogin.service;

import com.lm.SpringSecLogin.model.Product;                // Import the Product model, a JPA entity class that maps to the database table
import java.util.List;                                     // Import the List interface to work with collections
import java.util.Optional;                                 // Import the Optional class to work with null values and avoid NullPointerException

/**
 * The ProductService interface defines methods for CRUD operations on the Product entity.
 */

public interface ProductService
{
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product saveProduct(Product product);

    void deleteProduct(Long id);
}