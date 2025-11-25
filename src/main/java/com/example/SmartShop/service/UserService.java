package com.example.SmartShop.service;

import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.User;
import com.example.SmartShop.repository.AdminRepository;
import com.example.SmartShop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public UserService(UserRepository userRepository,
                       AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public Optional<User> authenticate(String username, String password) {

        Optional<User> client = userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password) && u.isActive());

        if (client.isPresent()) return client;

        Optional<Admin> admin = adminRepository.findByUsername(username)
                .filter(a -> a.getPassword().equals(password));

        return admin.map(a -> (User) a);
    }
}
