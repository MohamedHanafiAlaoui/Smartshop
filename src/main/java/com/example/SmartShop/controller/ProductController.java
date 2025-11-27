package com.example.SmartShop.controller;


import com.example.SmartShop.dto.ClientDto;
import com.example.SmartShop.dto.ProductDto;
import com.example.SmartShop.mapper.ProductMapper;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Product;
import com.example.SmartShop.service.AdminService;
import com.example.SmartShop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final AdminService adminService;

    public ProductController(ProductService productService, AdminService adminService) {
        this.productService = productService;
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
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto dto, HttpServletRequest request)
    {
        Admin  admin = getAdminFromSession(request);

        Product product = ProductMapper.toEntity(dto);
        Product saved = productService.createProduct(product,admin);
        return ResponseEntity.ok(ProductMapper.toDto(saved));

    }


    @PutMapping("/{id}")
    public  ResponseEntity<ProductDto> updateProduct(@PathVariable  Long id,@RequestBody ProductDto dto, HttpServletRequest request)
    {
        Admin  admin = getAdminFromSession(request);
        Product update = productService.updateProduct(id,dto,admin);
        return ResponseEntity.ok(ProductMapper.toDto(update));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable  Long id, HttpServletRequest request)
    {
        Admin  admin = getAdminFromSession(request);

        Product product = productService.getProductById(id,admin);

        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(HttpServletRequest request)
    {
        Admin  admin = getAdminFromSession(request);
        List<ProductDto> products =productService.getAllProduct(admin)
                .stream().map(ProductMapper::toDto).collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }


}
