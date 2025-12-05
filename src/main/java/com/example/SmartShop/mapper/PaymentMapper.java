package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.PaymentDto;
import com.example.SmartShop.model.entitie.Payment;
import org.mapstruct.*;
import com.example.SmartShop.model.entitie.Order;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "order.id", target = "orderId")
    PaymentDto toDto(Payment payment);

    @Mapping(source = "orderId", target = "order")
    Payment toEntity(PaymentDto dto);

    default Order mapOrder(Long orderId) {
        if (orderId == null) return null;
        Order o = new Order();
        o.setId(orderId);
        return o;
    }
}
