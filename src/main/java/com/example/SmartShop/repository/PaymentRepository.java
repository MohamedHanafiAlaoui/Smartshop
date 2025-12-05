package com.example.SmartShop.repository;

import com.example.SmartShop.model.entitie.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository  extends CrudRepository<Payment, Integer> {
}
