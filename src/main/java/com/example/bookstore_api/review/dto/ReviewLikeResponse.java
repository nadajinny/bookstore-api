package com.example.bookstore_api.review.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewLikeResponse {

    private Long reviewId;
    private long likeCount;
    private String message;
    private LocalDateTime occurredAt;
}
