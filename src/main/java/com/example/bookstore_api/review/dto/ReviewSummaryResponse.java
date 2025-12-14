package com.example.bookstore_api.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewSummaryResponse {

    private Long reviewId;
    private Long bookId;
    private String bookTitle;
    private int rating;
    private String content;
    private long likeCount;
    private boolean liked;
    private LocalDateTime createdAt;
}
