package com.example.SmartShop.model.entitie;

import com.example.SmartShop.model.enums.PaymentMethod;
import com.example.SmartShop.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroPaiement;

    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDate datePaiement;

    private LocalDate dateEncaissement;

    private String reference;

    private String banque;

    private LocalDate echeance;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
