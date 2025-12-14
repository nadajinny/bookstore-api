package com.example.bookstore_api.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FavoriteResponse {

    private Long favoriteId;
    private Long bookId;
    private String bookTitle;
    private String author;
}
