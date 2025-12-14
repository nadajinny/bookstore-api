package com.example.bookstore_api.order.dto;

import com.example.bookstore_api.order.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime orderedAt;
}
