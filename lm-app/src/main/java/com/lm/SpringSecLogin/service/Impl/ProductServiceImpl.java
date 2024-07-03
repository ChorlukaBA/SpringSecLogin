package com.lm.SpringSecLogin.service.Impl;

import com.lm.SpringSecLogin.model.Product;                         // Import the Product model, a JPA entity class that maps to the database table
import com.lm.SpringSecLogin.repository.ProductRepository;          // Import the ProductRepository interface that extends JpaRepository, which provides methods for CRUD operations
import com.lm.SpringSecLogin.service.ProductService;                // Import the ProductService interface that defines methods for CRUD operations
import org.springframework.beans.factory.annotation.Autowired;      // Import the @Autowired annotation to inject the ProductRepository bean
import org.springframework.stereotype.Service;                      // Import the @Service annotation to indicate that the class is a service

import java.util.List;                                            // Import the List interface to work with collections
import java.util.Optional;                                        // Import the Optional class to work with null values and avoid NullPointerException

/**
 * The ProductServiceImpl class implements the ProductService interface.
 * It provides methods for CRUD operations on the Product entity.
 */

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
}