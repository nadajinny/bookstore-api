package com.example.bookstore_api.user.controller;

import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.user.dto.UserProfileResponse;
import com.example.bookstore_api.user.dto.UserRegisterRequest;
import com.example.bookstore_api.user.dto.UserUpdateRequest;
import com.example.bookstore_api.user.dto.UserWithdrawalRequest;
import com.example.bookstore_api.user.entity.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "사용자 관리 API")
public class UserController {

    @PostMapping
    @Operation(summary = "사용자 회원가입")
    public ApiResponse<UserProfileResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserProfileResponse response = UserProfileResponse.builder()
            .id(1L)
            .email(request.getEmail())
            .name(request.getName())
            .role(UserRole.USER)
            .build();
        return ApiResponse.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "내 정보 조회")
    public ApiResponse<UserProfileResponse> getProfile() {
        UserProfileResponse response = UserProfileResponse.builder()
            .id(1L)
            .email("user@example.com")
            .name("홍길동")
            .role(UserRole.USER)
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/me")
    @Operation(summary = "내 정보 수정")
    public ApiResponse<UserProfileResponse> updateProfile(
        @Valid @RequestBody UserUpdateRequest request
    ) {
        UserProfileResponse response = UserProfileResponse.builder()
            .id(1L)
            .email("user@example.com")
            .name(request.getName() != null ? request.getName() : "홍길동")
            .role(UserRole.USER)
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/me/soft-delete")
    @Operation(summary = "소프트 삭제")
    public ApiResponse<Void> softDelete() {
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/me/permanent")
    @Operation(summary = "영구 삭제")
    public ApiResponse<Void> permanentDelete(
        @Valid @RequestBody UserWithdrawalRequest request
    ) {
        return ApiResponse.ok(null);
    }
}
