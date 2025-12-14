package com.example.bookstore_api.order.controller;

import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.order.dto.OrderItemResponse;
import com.example.bookstore_api.order.dto.OrderRequest;
import com.example.bookstore_api.order.dto.OrderResponse;
import com.example.bookstore_api.order.dto.OrderStatusUpdateRequest;
import com.example.bookstore_api.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "주문 API")
public class OrderController {

    @PostMapping
    @Operation(summary = "주문 생성")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = OrderResponse.builder()
            .orderId(1001L)
            .status(OrderStatus.PENDING)
            .totalAmount(BigDecimal.valueOf(56000))
            .orderedAt(LocalDateTime.now())
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "주문 상태 변경")
    public ApiResponse<OrderResponse> updateOrderStatus(
        @PathVariable Long orderId,
        @Valid @RequestBody OrderStatusUpdateRequest request
    ) {
        OrderResponse response = OrderResponse.builder()
            .orderId(orderId)
            .status(request.getStatus())
            .totalAmount(BigDecimal.valueOf(56000))
            .orderedAt(LocalDateTime.now().minusDays(1))
            .build();
        return ApiResponse.ok(response);
    }

    @GetMapping("/items")
    @Operation(summary = "내 주문 항목 전체 조회")
    public ApiResponse<List<OrderItemResponse>> getMyOrderItems() {
        List<OrderItemResponse> responses = List.of(
            OrderItemResponse.builder()
                .orderId(1001L)
                .orderItemId(1L)
                .bookId(1L)
                .bookTitle("객체지향의 사실과 오해")
                .quantity(1)
                .price(BigDecimal.valueOf(18000))
                .status(OrderStatus.SHIPPED)
                .build(),
            OrderItemResponse.builder()
                .orderId(1002L)
                .orderItemId(2L)
                .bookId(2L)
                .bookTitle("Effective Java 3/E")
                .quantity(1)
                .price(BigDecimal.valueOf(38000))
                .status(OrderStatus.PENDING)
                .build()
        );
        return ApiResponse.ok(responses);
    }
}
