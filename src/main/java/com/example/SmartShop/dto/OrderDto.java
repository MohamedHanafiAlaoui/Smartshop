package com.example.SmartShop.dto;

import com.example.SmartShop.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String numeroCommande;
    private LocalDateTime dateCreation;
    private OrderStatus status;
    private BigDecimal sousTotalHT;
    private BigDecimal montantRemise;
    private BigDecimal montantHTApresRemise;
    private BigDecimal tva;
    private BigDecimal totalTTC;
    private BigDecimal montantRestant;
    private String codePromo;
    private Long clientId;
    private String clientName;
    private List<OrderItemDto> items;
}