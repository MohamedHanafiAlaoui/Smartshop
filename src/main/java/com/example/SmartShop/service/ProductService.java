package com.example.SmartShop.service;

import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Product;
import com.example.SmartShop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    private  final ProductRepository productRepository;
    private  final  AdminService adminService;
    public ProductService(ProductRepository productRepository, AdminService adminService) {
        this.productRepository = productRepository;
        this.adminService = adminService;
    }

    public Product createProduct(Product product , Admin admin)
    {
        if (!adminService.hasPermission(admin,"createProduct"))
        {
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "You don't have permission to perform this action");

        }

        return productRepository.save(product);
    }
}
