package com.example.SmartShop.controller;

import com.example.SmartShop.dto.PaymentDto;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private Admin getAdminFromSession(HttpServletRequest request) {
        Long adminId = (Long) request.getSession().getAttribute("userId");
        String username = (String) request.getSession().getAttribute("userType");

        if (adminId == null || !"Admin".equals(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        Admin admin = new Admin();
        admin.setId(adminId);
        return admin;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@RequestBody PaymentDto dto, HttpServletRequest request) {
        Admin admin = getAdminFromSession(request);

        PaymentDto savedPayment = paymentService.addPayment(dto, admin);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
}
