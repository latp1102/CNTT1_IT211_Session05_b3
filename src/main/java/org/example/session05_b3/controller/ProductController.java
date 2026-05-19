package org.example.session05_b3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.session05_b3.exception.ProductNotFoundException;
import org.example.session05_b3.model.Product;
import org.example.session05_b3.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(product);
    }
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        product.setId(null);
        Product save = productRepository.save(product);
        return ResponseEntity.created(URI.create("/products/" + save.getId())).body(save);
    }
}
