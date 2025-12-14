package com.example.bookstore_api.wishlist.service;

import com.example.bookstore_api.wishlist.dto.WishlistActionResponse;
import com.example.bookstore_api.wishlist.dto.WishlistAddRequest;
import com.example.bookstore_api.wishlist.dto.WishlistItemResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    public WishlistActionResponse add(Long userId, WishlistAddRequest request) {
        return WishlistActionResponse.builder()
            .bookId(request.getBookId())
            .message("위시리스트에 도서를 추가했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public List<WishlistItemResponse> getWishlist(Long userId) {
        return List.of(
            WishlistItemResponse.builder()
                .bookId(1L)
                .title("객체지향의 사실과 오해")
                .author("조영호")
                .addedAt(LocalDateTime.now().minusDays(3))
                .build(),
            WishlistItemResponse.builder()
                .bookId(2L)
                .title("클린 아키텍처")
                .author("로버트 C. 마틴")
                .addedAt(LocalDateTime.now().minusDays(1))
                .build()
        );
    }

    public WishlistActionResponse remove(Long userId, Long bookId) {
        return WishlistActionResponse.builder()
            .bookId(bookId)
            .message("위시리스트에서 도서를 삭제했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }
}
