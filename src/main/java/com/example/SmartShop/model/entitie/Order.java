package com.example.SmartShop.model.entitie;

import com.example.SmartShop.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCommande;

    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal sousTotalHT;

    private BigDecimal montantRemise;

    private BigDecimal montantHTApresRemise;

    private BigDecimal tva;

    private BigDecimal totalTTC;

    private BigDecimal montantRestant;

    private String codePromo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
