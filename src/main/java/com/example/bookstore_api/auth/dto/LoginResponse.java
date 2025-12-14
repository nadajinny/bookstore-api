package com.example.bookstore_api.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    @Builder.Default
    private final String tokenType = "Bearer";
    private final String accessToken;
    private final long accessTokenExpiresIn;
    private final String refreshToken;
    private final long refreshTokenExpiresIn;
}
