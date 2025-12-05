package com.example.SmartShop.service;

import com.example.SmartShop.dto.PaymentDto;
import com.example.SmartShop.mapper.PaymentMapper;
import com.example.SmartShop.model.entitie.*;
import com.example.SmartShop.model.enums.PaymentStatus;
import com.example.SmartShop.model.enums.PaymentMethod;
import com.example.SmartShop.model.enums.OrderStatus;
import com.example.SmartShop.repository.AdminRepository;
import com.example.SmartShop.repository.OrderRepository;
import com.example.SmartShop.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final AdminRepository adminRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(OrderRepository orderRepository,
                          PaymentRepository paymentRepository,
                          AdminRepository adminRepository,
                          PaymentMapper paymentMapper) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.adminRepository = adminRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto addPayment(PaymentDto dto, Admin admin) {

        if (!adminRepository.existsById(admin.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin non autorisé");
        }

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande introuvable"));

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Commande déjà confirmée : impossible d’ajouter un paiement");
        }

        if (dto.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Montant invalide");
        }

        if (dto.getMethod() == PaymentMethod.ESPECES && dto.getMontant().compareTo(BigDecimal.valueOf(20000)) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Le paiement en espèces dépasse la limite légale de 20.000 DH");
        }

        Payment payment = paymentMapper.toEntity(dto);
        payment.setOrder(order);

        if (payment.getDatePaiement() == null) {
            payment.setDatePaiement(LocalDate.now());
        }

        // تحديد حالة الدفع حسب الوسيلة
        if (payment.getMethod() == PaymentMethod.ESPECES) {
            payment.setStatus(PaymentStatus.ENCAISSE);
        } else {
            payment.setStatus(PaymentStatus.EN_ATTENTE);
        }


        BigDecimal newRestant = order.getMontantRestant().subtract(payment.getMontant());
        order.setMontantRestant(newRestant.max(BigDecimal.ZERO));


        if (order.getMontantRestant().compareTo(BigDecimal.ZERO) == 0 &&
                payment.getMethod() == PaymentMethod.ESPECES) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else if (order.getMontantRestant().compareTo(BigDecimal.ZERO) == 0) {
            order.setStatus(OrderStatus.PENDING);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toDto(payment);
    }
}
