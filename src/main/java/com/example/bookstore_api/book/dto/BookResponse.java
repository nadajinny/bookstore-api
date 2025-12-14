package com.example.bookstore_api.book.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookResponse {

    private Long bookId;
    private String title;
    private String author;
    private BigDecimal price;
    private String description;
    private boolean available;
}
