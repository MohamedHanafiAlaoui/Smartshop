package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.ClientDto;
import com.example.SmartShop.model.entitie.Client;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClientMapper {
    private ClientMapper(){};

    public static ClientDto toDto(Client client) {
    if (client == null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Client is null");
    {
        return ClientDto.builder()
                .id(client.getId())
                .username(client.getUsername())
                .nom(client.getNom())
                .email(client.getEmail())
                .adresse(client.getAdresse())
                .tier(client.getTier())
                .role(client.getRole())
                .ice(client.getIce())
                .password(client.getPassword())
                .build();
    }
    }

    public static Client toEntity(ClientDto dto) {
        if (dto == null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Client is null");

        return Client.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .nom(dto.getNom())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .telephone(dto.getTelephone())
                .ice(dto.getIce())
                .active(true)
                .build();
    }
}
