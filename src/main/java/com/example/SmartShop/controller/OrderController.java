package com.example.SmartShop.controller;

import com.example.SmartShop.dto.OrderDto;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.service.AdminService;
import com.example.SmartShop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final AdminService adminService;

    public OrderController(OrderService orderService, AdminService adminService) {
        this.orderService = orderService;
        this.adminService = adminService;
    }

    private Admin getAdminFromSession(HttpServletRequest request){
        Long adminId = (Long) request.getSession().getAttribute("userId");
        String username = (String) request.getSession().getAttribute("userType");

        if(adminId == null || "Admin".equals(username))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return adminService.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin Not Found"));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, HttpServletRequest request){
        Admin admin = getAdminFromSession(request);
        OrderDto created = orderService.createOrder(orderDto, admin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(HttpServletRequest request){
        Admin admin = getAdminFromSession(request);
        List<OrderDto> orders = orderService.getAllOrders(admin);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id, HttpServletRequest request){
        Admin admin = getAdminFromSession(request);
        OrderDto order = orderService.findOrderById(id, admin);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto, HttpServletRequest request){
        Admin admin = getAdminFromSession(request);
        orderDto.setId(id);
        OrderDto updated = orderService.updateOrder(orderDto, admin);
        return ResponseEntity.ok(updated);
    }
}
