package com.example.bookstore_api.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewDetailResponse {

    private Long reviewId;
    private Long bookId;
    private String bookTitle;
    private String reviewerName;
    private int rating;
    private String content;
    private long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
