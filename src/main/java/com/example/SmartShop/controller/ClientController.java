package com.example.SmartShop.controller;

import com.example.SmartShop.dto.ClientDto;
import com.example.SmartShop.mapper.ClientMapper;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Client;
import com.example.SmartShop.service.AdminService;
import com.example.SmartShop.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final AdminService adminService;

    public ClientController(ClientService clientService, AdminService adminService) {
        this.clientService = clientService;
        this.adminService = adminService;
    }

    private Admin getAdminFromSession(HttpServletRequest request) {
        Long adminId = (Long) request.getSession().getAttribute("userId");
        String userType = (String) request.getSession().getAttribute("userType");

        if (adminId == null || !"ADMIN".equals(userType)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        return adminService.findById(adminId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto dto, HttpServletRequest request) {
        Admin admin = getAdminFromSession(request);

        Client client = ClientMapper.toEntity(dto);
        Client saved = clientService.createClient(client, admin);
        return ResponseEntity.ok(ClientMapper.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients(HttpServletRequest request)
    {
        Admin admin = getAdminFromSession(request);
        List<ClientDto> dtos = clientService.getAllClients(admin);

        return ResponseEntity.ok(dtos);
    }

}
