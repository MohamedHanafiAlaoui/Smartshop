package com.example.SmartShop.service;

import com.example.SmartShop.dto.ClientProfileDto;
import com.example.SmartShop.mapper.OrderMapper;
import com.example.SmartShop.model.entitie.Client;
import com.example.SmartShop.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerService {

    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;

    public CustomerService(ClientRepository clientRepository, OrderMapper orderMapper) {
        this.clientRepository = clientRepository;
        this.orderMapper = orderMapper;
    }

    public ClientProfileDto getClientProfile(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND , "Client not found"));

        ClientProfileDto dto = new ClientProfileDto();
        dto.setNom(client.getNom());
        dto.setEmail(client.getEmail());
        dto.setTelephone(client.getTelephone());
        dto.setAdresse(client.getAdresse());
        dto.setTier(client.getTier());
        dto.setTotalOrders(client.getTotalOrders());
        dto.setTotalSpent(client.getTotalSpent());
        dto.setOrders(client.getOrders().stream()
                .map(orderMapper::toDto)
                .toList());
        return dto;
    }
}
