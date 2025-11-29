package com.example.SmartShop.service;

import com.example.SmartShop.dto.OrderDto;
import com.example.SmartShop.model.entitie.Admin;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto, Admin admin);

    OrderDto findOrderById(Long id , Admin admin);

    List<OrderDto> getAllOrders( Admin admin);

    OrderDto updateOrder(OrderDto orderDto, Admin admin);



}
