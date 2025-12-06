package com.example.SmartShop.service;

import com.example.SmartShop.dto.ProductDto;
import com.example.SmartShop.model.entitie.Admin;
import com.example.SmartShop.model.entitie.Product;
import com.example.SmartShop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    public   Product updateProduct(Long id, ProductDto productDto , Admin admin)
    {
        if (!adminService.hasPermission(admin,"updateProduct"))
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN , "You don't have permission to perform this action");

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "Product not found"));

        if (product.getName() != null) product.setName(productDto.getName());
        if (product.getDescription() != null) product.setDescription(productDto.getDescription());
        if (product.getPrixUnitaireHT() != null) product.setPrixUnitaireHT(productDto.getPrixUnitaireHT());
        if (product.getStock() != 0)  product.setStock(productDto.getStock());



            return productRepository.save(product);
    }


    public Product getProductById(Long id , Admin admin)
    {
        if(!adminService.hasPermission(admin,"getProductById"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN , "You don't have permission to perform this action");

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "Product not found"));

        if(product.getDeleted()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND , "Product is already deleted");

        return product;

    }

    public List<Product>  getAllProduct(Admin admin)
    {
        if (!adminService.hasPermission(admin,"getAllProduct"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN , "You don't have permission to perform this action");

        return productRepository.findAll().stream()
                .filter(product -> product.getDeleted()!=true)
                .collect(Collectors.toList());
    }

    public String deleteProduct(Long id, Admin admin)
    {
        if (!adminService.hasPermission(admin,"deleteProduct"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN , "You don't have permission to perform this action");

        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND , "Product not found"));

        product.setDeleted(true);

        productRepository.save(product);

        return "this product is deleted";
    }


    public Page<Product> getProductsWithFilters(
            String search,
            Double minPrice,
            Double maxPrice,
            int page,
            int size,
            Admin admin
    ) {

        if (!adminService.hasPermission(admin, "getAllProduct")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "You don't have permission to perform this action");
        }

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findByFilters(
                (search == null || search.isEmpty()) ? null : search,
                minPrice,
                maxPrice,
                pageable
        );
    }

}
