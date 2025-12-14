package com.example.bookstore_api.user.controller;

import com.example.bookstore_api.auth.dto.LoginRequest;
import com.example.bookstore_api.auth.dto.LoginResponse;
import com.example.bookstore_api.auth.dto.RefreshTokenRequest;
import com.example.bookstore_api.auth.service.AuthService;
import com.example.bookstore_api.auth.security.CustomUserDetails;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.user.dto.UserActionResponse;
import com.example.bookstore_api.user.dto.UserProfileResponse;
import com.example.bookstore_api.user.dto.UserRegisterRequest;
import com.example.bookstore_api.user.dto.UserUpdateRequest;
import com.example.bookstore_api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "사용자 관리 API")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "사용자 회원가입")
    public ApiResponse<UserProfileResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ApiResponse.ok(userService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ApiResponse<UserActionResponse> logout(
        @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        authService.logout(authorizationHeader);
        UserActionResponse response = UserActionResponse.builder()
            .message("로그아웃이 완료되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
        return ApiResponse.ok(response);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "토큰 재발급")
    public ApiResponse<LoginResponse> refreshToken(
        @Valid @RequestBody RefreshTokenRequest request
    ) {
        return ApiResponse.ok(authService.refresh(request));
    }

    @GetMapping("/me")
    @Operation(summary = "내 프로필 조회")
    public ApiResponse<UserProfileResponse> getProfile(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(userService.getProfile(requireUserId(userDetails)));
    }

    @PatchMapping("/me")
    @Operation(summary = "내 프로필 수정")
    public ApiResponse<UserProfileResponse> updateProfile(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody UserUpdateRequest request
    ) {
        return ApiResponse.ok(userService.updateProfile(requireUserId(userDetails), request));
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원 탈퇴")
    public ApiResponse<UserActionResponse> deleteAccount(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        userService.softDelete(requireUserId(userDetails));
        UserActionResponse response = UserActionResponse.builder()
            .message("회원 탈퇴가 완료되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
        return ApiResponse.ok(response);
    }

    private Long requireUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorized("로그인이 필요합니다.");
        }
        return userDetails.getId();
    }
}
