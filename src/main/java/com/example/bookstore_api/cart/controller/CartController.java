package com.example.bookstore_api.cart.controller;

import com.example.bookstore_api.cart.dto.CartItemRequest;
import com.example.bookstore_api.cart.dto.CartItemResponse;
import com.example.bookstore_api.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Carts", description = "장바구니 API")
public class CartController {

    @GetMapping("/items")
    @Operation(summary = "장바구니 항목 전체 조회")
    public ApiResponse<List<CartItemResponse>> getCartItems() {
        List<CartItemResponse> items = List.of(
            CartItemResponse.builder()
                .bookId(1L)
                .title("객체지향의 사실과 오해")
                .quantity(1)
                .price(BigDecimal.valueOf(18000))
                .build(),
            CartItemResponse.builder()
                .bookId(2L)
                .title("Effective Java 3/E")
                .quantity(2)
                .price(BigDecimal.valueOf(45000))
                .build()
        );
        return ApiResponse.ok(items);
    }

    @PostMapping("/items")
    @Operation(summary = "장바구니 항목 추가")
    public ApiResponse<CartItemResponse> addCartItem(@Valid @RequestBody CartItemRequest request) {
        CartItemResponse response = CartItemResponse.builder()
            .bookId(request.getBookId())
            .title("Sample Book")
            .quantity(request.getQuantity())
            .price(BigDecimal.valueOf(10000L))
            .build();
        return ApiResponse.ok(response);
    }

    @PutMapping("/items")
    @Operation(summary = "장바구니 수량 변경")
    public ApiResponse<CartItemResponse> updateCartItem(@Valid @RequestBody CartItemRequest request) {
        CartItemResponse response = CartItemResponse.builder()
            .bookId(request.getBookId())
            .title("Sample Book")
            .quantity(request.getQuantity())
            .price(BigDecimal.valueOf(10000L))
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/items/{bookId}")
    @Operation(summary = "장바구니 항목 삭제")
    public ApiResponse<Void> deleteCartItem(@PathVariable Long bookId) {
        return ApiResponse.ok(null);
    }
}
