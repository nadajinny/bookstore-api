package com.example.bookstore_api.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithdrawalRequest {

    @NotBlank
    private String reason;

    private boolean immediate;
}
