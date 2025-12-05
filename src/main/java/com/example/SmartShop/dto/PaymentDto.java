package com.example.SmartShop.dto;

import com.example.SmartShop.model.enums.PaymentMethod;
import com.example.SmartShop.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    private Long id;
    private Integer numeroPaiement;
    private BigDecimal montant;
    private PaymentMethod method;
    private PaymentStatus status;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private String reference;
    private String banque;
    private LocalDate echeance;

    private Long orderId;
}
