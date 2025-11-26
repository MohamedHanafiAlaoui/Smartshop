package com.example.SmartShop.dto;


import com.example.SmartShop.model.entitie.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal prixUnitaireHT;
    private int stock;


    public static  ProductDto toDto(Product product)
    {
        if (product == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product is null");

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .prixUnitaireHT(product.getPrixUnitaireHT())
                .stock(product.getStock())
                .build();
    }
}
