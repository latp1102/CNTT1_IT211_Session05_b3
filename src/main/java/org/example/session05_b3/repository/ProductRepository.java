package org.example.session05_b3.repository;

import org.example.session05_b3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
