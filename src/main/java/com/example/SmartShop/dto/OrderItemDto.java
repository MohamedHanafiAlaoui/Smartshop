package com.example.SmartShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantite;
    private BigDecimal prixUnitaireHT;
    private BigDecimal totalLigneHT;
}

