package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.OrderDto;
import com.example.SmartShop.model.entitie.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order entity);
    Order toEntity(OrderDto dto);

    List<OrderDto> toDtoList(List<Order> entities);
    List<Order> toEntityList(List<OrderDto> dtos);

    @AfterMapping
    default void linkItems(@MappingTarget Order order) {
        if (order != null && order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
    }
}
