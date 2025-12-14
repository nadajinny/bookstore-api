package com.example.bookstore_api.wishlist.controller;

import com.example.bookstore_api.auth.security.CustomUserDetails;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.wishlist.dto.WishlistActionResponse;
import com.example.bookstore_api.wishlist.dto.WishlistAddRequest;
import com.example.bookstore_api.wishlist.dto.WishlistItemResponse;
import com.example.bookstore_api.wishlist.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "위시리스트 API")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "위시리스트 추가")
    public ApiResponse<WishlistActionResponse> addWishlistItem(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody WishlistAddRequest request
    ) {
        return ApiResponse.ok(wishlistService.add(requireUserId(userDetails), request));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "위시리스트 조회")
    public ApiResponse<List<WishlistItemResponse>> getWishlist(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(wishlistService.getWishlist(requireUserId(userDetails)));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "위시리스트 삭제")
    public ApiResponse<WishlistActionResponse> removeWishlistItem(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long bookId
    ) {
        return ApiResponse.ok(wishlistService.remove(requireUserId(userDetails), bookId));
    }

    private Long requireUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorized("로그인이 필요합니다.");
        }
        return userDetails.getId();
    }
}
