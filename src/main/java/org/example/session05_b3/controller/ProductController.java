package org.example.session05_b3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.session05_b3.exception.ProductNotFoundException;
import org.example.session05_b3.model.Product;
import org.example.session05_b3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    // b3
    @Autowired
    private ProductRepository productRepository;
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
    // b4
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateFull(@PathVariable Long id, @Valid @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updated = productRepository.save(product);
        return ResponseEntity.ok(updated);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updatePartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product product = optional.get();
        if (updates.containsKey("name")) {
            String name = updates.get("name").toString();
            if (name.isBlank()) {
                return ResponseEntity.badRequest().build();
            }
            product.setName(name);
        }
        if (updates.containsKey("price")) {
            double price = Double.parseDouble(updates.get("price").toString());
            if (price <= 0) {
                return ResponseEntity.badRequest().build();
            }
            product.setPrice(price);
        }
        Product updated = productRepository.save(product);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
