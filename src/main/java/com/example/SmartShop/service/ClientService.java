package com.example.SmartShop.service;

import com.example.SmartShop.dto.ClientDto;
import com.example.SmartShop.mapper.ClientMapper;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Client;
import com.example.SmartShop.model.entitie.User;
import com.example.SmartShop.repository.ClientRepository;
import com.example.SmartShop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AdminService adminService;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, AdminService adminService, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.adminService = adminService;
        this.userRepository = userRepository;
    }

    public Client createClient( Client client, Admin admin) {


        if (!adminService.hasPermission(admin, "CREATE_CLIENT")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permission to create client");
        }
        if (clientRepository.existsByUsername(client.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return clientRepository.save(client);
    }

    public List<ClientDto> getAllClients(Admin admin)
    {
        if (!adminService.hasPermission(admin, "GET_ALL_CLIENTS"))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No permission to get all clients");
        }


        return clientRepository.findAll()
                .stream().map(ClientMapper::toDto)
                .toList();
    }

    public ClientDto getClientById(Long id,Admin admin)
    {
        if (!adminService.hasPermission(admin, "GET_CLIENT_BY_ID"))
        {
            throw new  ResponseStatusException(HttpStatus.FORBIDDEN, "No permission to get client by id");
        }
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent())
        {
            return ClientMapper.toDto(client.get());
        }
        return null;
    }

    public ClientDto updateClientById(Long id, ClientDto clientDto, Admin admin)
    {
        if (!adminService.hasPermission(admin, "UPDATE_CLIENT_BY_ID"))
        {
            throw new  ResponseStatusException(HttpStatus.FORBIDDEN, "No permission to update client");
        }
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        if (clientDto.getUsername() != null) client.setUsername(clientDto.getUsername());
        if (clientDto.getNom() != null) client.setNom(clientDto.getNom());
        if (clientDto.getEmail() != null) client.setEmail(clientDto.getEmail());
        if (clientDto.getTelephone() != null) client.setTelephone(clientDto.getTelephone());
        if (clientDto.getAdresse() != null) client.setAdresse(clientDto.getAdresse());
        if (clientDto.getTier() != null) client.setTier(clientDto.getTier());
        if (clientDto.getIce() != null) client.setIce(clientDto.getIce());


        Client saved = clientRepository.save(client);

        return ClientDto.toDto(saved);
    }





}
