package com.example.SmartShop;

import com.example.SmartShop.dto.ProductDto;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Product;
import com.example.SmartShop.repository.ProductRepository;
import com.example.SmartShop.service.AdminService;
import com.example.SmartShop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private ProductService productService;

    private Admin admin;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        product = new Product();
        product.setId(1L);
        product.setName("PC Gamer");
        product.setDescription("Fast PC");
        product.setPrixUnitaireHT(new BigDecimal("1200.0"));
        product.setStock(10);
        product.setDeleted(false);

        productDto = new ProductDto();
        productDto.setName("New PC");
        productDto.setDescription("Updated");
        productDto.setPrixUnitaireHT(new BigDecimal("1500.0"));
        productDto.setStock(20);
    }

    @Test
    void testCreateProduct_Success() {
        when(adminService.hasPermission(admin, "createProduct")).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product, admin);

        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProduct_NoPermission() {
        when(adminService.hasPermission(admin, "createProduct")).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> productService.createProduct(product, admin));
    }

    @Test
    void testUpdateProduct_Success() {
        when(adminService.hasPermission(admin, "updateProduct")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);

        Product updated = productService.updateProduct(1L, productDto, admin);

        assertEquals("New PC", updated.getName());
        assertEquals("Updated", updated.getDescription());
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(adminService.hasPermission(admin, "updateProduct")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> productService.updateProduct(1L, productDto, admin));
    }

    @Test
    void testGetProductById_Success() {
        when(adminService.hasPermission(admin, "getProductById")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L, admin);

        assertNotNull(result);
        assertEquals("PC Gamer", result.getName());
    }

    @Test
    void testGetProductById_Deleted() {
        product.setDeleted(true);

        when(adminService.hasPermission(admin, "getProductById")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(ResponseStatusException.class,
                () -> productService.getProductById(1L, admin));
    }

    @Test
    void testGetProductById_NoPermission() {
        when(adminService.hasPermission(admin, "getProductById")).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> productService.getProductById(1L, admin));
    }

    @Test
    void testGetAllProducts_Success() {
        Product p2 = new Product();
        p2.setDeleted(true);

        when(adminService.hasPermission(admin, "getAllProduct")).thenReturn(true);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product, p2));

        List<Product> result = productService.getAllProduct(admin);

        assertEquals(1, result.size());
        assertFalse(result.get(0).getDeleted());
    }

    @Test
    void testGetAllProducts_NoPermission() {
        when(adminService.hasPermission(admin, "getAllProduct")).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> productService.getAllProduct(admin));
    }

    @Test
    void testDeleteProduct_Success() {
        when(adminService.hasPermission(admin, "deleteProduct")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        String result = productService.deleteProduct(1L, admin);

        assertEquals("this product is deleted", result);
        assertTrue(product.getDeleted());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(adminService.hasPermission(admin, "deleteProduct")).thenReturn(true);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> productService.deleteProduct(1L, admin));
    }
}
