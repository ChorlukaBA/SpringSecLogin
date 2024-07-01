package com.lm.SpringSecLogin.controllers;

import com.lm.SpringSecLogin.model.Product;
import com.lm.SpringSecLogin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}