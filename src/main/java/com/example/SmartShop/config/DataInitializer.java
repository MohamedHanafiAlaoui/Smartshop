package com.example.SmartShop.config;

import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;

    public DataInitializer(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!adminRepository.existsByUsername("admin")) {

            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("Admin");
            admin.setNom("Super");
            admin.setPrenom("Admin");
            admin.setPermissions(List.of("ALL"));

            adminRepository.save(admin);
            System.out.println(" First Admin created with BCrypt password");
        }
    }
}
