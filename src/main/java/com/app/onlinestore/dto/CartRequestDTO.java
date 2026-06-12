package com.app.onlinestore.dto;

import lombok.Data;

@Data
public class CartRequestDTO {
    private Long productId;
    private Integer quantity;
}