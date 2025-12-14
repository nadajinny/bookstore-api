package com.example.bookstore_api.order.dto;

import com.example.bookstore_api.order.entity.OrderStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemResponse {

    private Long orderId;
    private Long orderItemId;
    private Long bookId;
    private String bookTitle;
    private int quantity;
    private BigDecimal price;
    private OrderStatus status;
}
