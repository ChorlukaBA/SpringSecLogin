package com.lm.SpringSecLogin.controllers;

import com.lm.SpringSecLogin.model.Product;                                        // Importing the Product model, which is the entity class
import com.lm.SpringSecLogin.service.ProductService;                               // Importing the ProductService, which is the service class
import org.springframework.beans.factory.annotation.Autowired;                     // Importing the @Autowired annotation, needed for dependency injection
import org.springframework.http.ResponseEntity;                                    // Importing the ResponseEntity class, which is used to return HTTP status codes
import org.springframework.web.bind.annotation.*;                                  // Importing the @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PathVariable, @RequestBody annotations

import java.util.List;                                             // Importing the List class, which is used to store a list of products
import java.util.Optional;                                         // Importing the Optional class. It is used to avoid NullPointerExceptions and to handle null values properly

/*
    * The ProductController class is a REST controller class that handles HTTP requests for the Product entity.
    * It contains methods to get all products, get a product by ID, add a product, update a product, and delete a product.
*/

@RestController
@RequestMapping("/api/products")                                 // The base URL for all the endpoints in this class
public class ProductController
{
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    // Here we define a GET endpoint to get all products in the database
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Here we define a GET endpoint to get a single product given its ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Here we define a POST endpoint to add a new product to the database
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Here we define a PUT endpoint to update an existing product in the database
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct)
    {
        Optional<Product> existingProductOptional = productService.getProductById(id);

        if(existingProductOptional.isPresent())
        {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updateProduct.getName());
            existingProduct.setPrice(updateProduct.getPrice());
            existingProduct.setDescription(updateProduct.getDescription());

            // Save the updated product
            Product savedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(savedProduct);
        }
        else
        {
            // Product not found
            return ResponseEntity.notFound().build();
        }
    }

    // Here we define a DELETE endpoint to delete a product from the database, given its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}