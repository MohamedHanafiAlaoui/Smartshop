package com.example.SmartShop.repository;

import com.example.SmartShop.model.entitie.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByUsername(String username);

    @Query("SELECT a FROM Admin a LEFT JOIN FETCH a.permissions WHERE a.username = :username")
    Optional<Admin> findByUsername(@Param("username") String username);}
