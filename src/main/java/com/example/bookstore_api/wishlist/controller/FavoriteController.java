package com.example.bookstore_api.wishlist.controller;

import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.wishlist.dto.FavoriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "도서 좋아요(위시리스트) API")
public class FavoriteController {

    @PostMapping("/{bookId}")
    @Operation(summary = "도서 좋아요 추가")
    public ApiResponse<FavoriteResponse> addFavorite(@PathVariable Long bookId) {
        FavoriteResponse response = FavoriteResponse.builder()
            .favoriteId(1L)
            .bookId(bookId)
            .bookTitle("샘플 도서")
            .author("저자")
            .build();
        return ApiResponse.ok(response);
    }

    @GetMapping
    @Operation(summary = "좋아요 목록 조회")
    public ApiResponse<List<FavoriteResponse>> getFavorites() {
        List<FavoriteResponse> favorites = List.of(
            FavoriteResponse.builder()
                .favoriteId(1L)
                .bookId(1L)
                .bookTitle("객체지향의 사실과 오해")
                .author("조영호")
                .build(),
            FavoriteResponse.builder()
                .favoriteId(2L)
                .bookId(2L)
                .bookTitle("클린 코드")
                .author("로버트 C. 마틴")
                .build()
        );
        return ApiResponse.ok(favorites);
    }

    @DeleteMapping("/{favoriteId}")
    @Operation(summary = "좋아요 취소")
    public ApiResponse<Void> removeFavorite(@PathVariable Long favoriteId) {
        return ApiResponse.ok(null);
    }
}
