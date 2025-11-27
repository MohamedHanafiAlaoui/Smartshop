package com.example.SmartShop.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite;

    private BigDecimal prixUnitaireHT;

    private BigDecimal totalLigneHT;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

