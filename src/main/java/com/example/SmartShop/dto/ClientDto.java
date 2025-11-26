package com.example.SmartShop.dto;

import com.example.SmartShop.model.entitie.Client;
import com.example.SmartShop.model.enums.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private Long id;
    private String username;
    private String nom;
    private String email;
    private String role;
    private String password;
    private String telephone;
    private String adresse;
    private CustomerTier tier;
    private String ice;
    private Long userId;


    public static ClientDto toDto(Client client)
    {
        if (client == null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Client is null");
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
