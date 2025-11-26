package com.example.SmartShop.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(columnDefinition = "TEXT")

    private String description;
    private BigDecimal prixUnitaireHT;
    private int stock;
    private Boolean deleted = false;
    private LocalDateTime creationDate = LocalDateTime.now();

}
