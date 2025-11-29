package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.OrderItemDto;
import com.example.SmartShop.model.entitie.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem entity);
    OrderItem toEntity(OrderItemDto dto);

    List<OrderItemDto> toDtoList(List<OrderItem> entities);
    List<OrderItem> toEntityList(List<OrderItemDto> dtos);
}