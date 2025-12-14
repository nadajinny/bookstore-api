package com.example.bookstore_api.book.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookSummaryResponse {

    private Long bookId;
    private String title;
    private BigDecimal price;
    private Integer stock;
    private double averageRating;
    private long reviewCount;
    private List<String> authors;
    private List<String> categories;
    private LocalDateTime createdAt;
}
