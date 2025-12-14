package com.example.bookstore_api.cart.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartItemResponse {

    private Long bookId;
    private String title;
    private int quantity;
    private BigDecimal price;
}
