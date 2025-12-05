package com.example.SmartShop.dto;

import com.example.SmartShop.model.enums.CustomerTier;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ClientProfileDto {
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private CustomerTier tier; // مستوى الولاء
    private Integer totalOrders;
    private BigDecimal totalSpent;
    private List<OrderDto> orders; // تاريخ الطلبات
}
