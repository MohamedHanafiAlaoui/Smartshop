package com.example.SmartShop.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public static OrderItem create(Product product, int qty) {
        OrderItem item = new OrderItem();

        item.product = product;
        item.quantite = qty;

        item.prixUnitaireHT = product.getPrixUnitaireHT();
        item.totalLigneHT   = product.getPrixUnitaireHT()
                .multiply(BigDecimal.valueOf(qty));

        return item;
    }
}

