package com.example.SmartShop.dto;

import java.util.List;

public class OrderRequest {
    private Long customerId;
    private List<OrderItemRequest> items;
}
