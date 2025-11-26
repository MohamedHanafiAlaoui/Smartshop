package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.ProductDto;
import com.example.SmartShop.model.entitie.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductMapper {
    private  ProductMapper() {
    }


    public static ProductDto toDto(Product product)
    {
        if (product == null) throw new NullPointerException("product is null");
        {
            return ProductDto.builder()
                    .id(product.getId())
                    .description(product.getDescription())
                    .prixUnitaireHT(product.getPrixUnitaireHT())
                    .stock(product.getStock())
                    .name(product.getName())
                    .build();
        }
    }

    public static Product toEntity(ProductDto dto)
    {
        if (dto == null) throw new ResponseStatusException(HttpStatus.CONFLICT,"product is null");

        return Product.builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .prixUnitaireHT(dto.getPrixUnitaireHT())
                .stock(dto.getStock())
                .name(dto.getName())
                .build();
    }
}
