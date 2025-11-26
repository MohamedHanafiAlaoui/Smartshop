package com.example.SmartShop.repository;

import com.example.SmartShop.model.entitie.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
