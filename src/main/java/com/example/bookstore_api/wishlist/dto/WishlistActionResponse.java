package com.example.bookstore_api.wishlist.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WishlistActionResponse {

    private Long bookId;
    private String message;
    private LocalDateTime occurredAt;
}
