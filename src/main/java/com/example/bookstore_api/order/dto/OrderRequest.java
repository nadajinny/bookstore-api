package com.example.bookstore_api.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @Valid
    @NotEmpty
    private List<OrderItemRequest> items;

    @NotBlank
    @Size(max = 100)
    private String recipientName;

    @NotBlank
    @Size(max = 255)
    private String shippingAddress;

    @NotBlank
    @Size(max = 50)
    private String paymentMethod;
}
