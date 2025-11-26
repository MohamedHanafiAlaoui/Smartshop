// AdminService.java
package com.example.SmartShop.service;

import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    public boolean hasPermission(Admin admin, String permission) {
        return admin.getPermissions().contains(permission) || admin.getPermissions().contains("ALL");
    }

    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new RuntimeException("Admin username already exists");
        }
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setUsername(updatedAdmin.getUsername());
                    admin.setNom(updatedAdmin.getNom());
                    admin.setPrenom(updatedAdmin.getPrenom());
                    admin.setPermissions(updatedAdmin.getPermissions());
                    return adminRepository.save(admin);
                })
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found");
        }
        adminRepository.deleteById(id);
    }
}
