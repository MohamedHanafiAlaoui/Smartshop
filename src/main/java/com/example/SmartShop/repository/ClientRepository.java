package com.example.SmartShop.repository;

import com.example.SmartShop.model.entitie.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
    boolean existsByUsername(String username);
}
