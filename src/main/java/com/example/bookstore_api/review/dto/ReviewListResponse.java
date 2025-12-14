package com.example.bookstore_api.review.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewListResponse {

    private double averageRating;
    private long totalReviews;
    private List<ReviewSummaryResponse> reviews;
}
