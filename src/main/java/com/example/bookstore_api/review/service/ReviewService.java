package com.example.bookstore_api.review.service;

import com.example.bookstore_api.review.dto.ReviewCreateRequest;
import com.example.bookstore_api.review.dto.ReviewDetailResponse;
import com.example.bookstore_api.review.dto.ReviewLikeResponse;
import com.example.bookstore_api.review.dto.ReviewListResponse;
import com.example.bookstore_api.review.dto.ReviewSummaryResponse;
import com.example.bookstore_api.review.dto.ReviewUpdateRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    public ReviewDetailResponse createReview(Long userId, ReviewCreateRequest request) {
        return ReviewDetailResponse.builder()
            .reviewId(2001L)
            .bookId(request.getBookId())
            .bookTitle("샘플 도서")
            .reviewerName("사용자" + userId)
            .rating(request.getRating())
            .content(request.getContent())
            .likeCount(0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public ReviewListResponse getBookReviews(Long bookId, String sort, String filter, Long userId) {
        List<ReviewSummaryResponse> reviews = List.of(
            ReviewSummaryResponse.builder()
                .reviewId(2001L)
                .bookId(bookId)
                .bookTitle("샘플 도서")
                .rating(5)
                .content("최고의 책입니다.")
                .likeCount(42)
                .liked(true)
                .createdAt(LocalDateTime.now().minusDays(2))
                .build(),
            ReviewSummaryResponse.builder()
                .reviewId(2002L)
                .bookId(bookId)
                .bookTitle("샘플 도서")
                .rating(4)
                .content("추천합니다.")
                .likeCount(10)
                .liked(false)
                .createdAt(LocalDateTime.now().minusDays(5))
                .build()
        );
        return ReviewListResponse.builder()
            .averageRating(4.5)
            .totalReviews(reviews.size())
            .reviews(reviews)
            .build();
    }

    public List<ReviewSummaryResponse> getMyReviews(Long userId) {
        return List.of(
            ReviewSummaryResponse.builder()
                .reviewId(2101L)
                .bookId(1L)
                .bookTitle("객체지향의 사실과 오해")
                .rating(5)
                .content("도움이 많이 되었어요")
                .likeCount(5)
                .liked(true)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build()
        );
    }

    public ReviewDetailResponse updateReview(Long userId, Long reviewId, ReviewUpdateRequest request) {
        return ReviewDetailResponse.builder()
            .reviewId(reviewId)
            .bookId(1L)
            .bookTitle("샘플 도서")
            .reviewerName("사용자" + userId)
            .rating(request.getRating() != null ? request.getRating() : 5)
            .content(request.getContent() != null ? request.getContent() : "업데이트된 내용")
            .likeCount(12)
            .createdAt(LocalDateTime.now().minusDays(7))
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public void deleteReview(Long userId, Long reviewId) {
        // Soft Delete 처리 로직이 들어갈 자리입니다.
    }

    public ReviewLikeResponse likeReview(Long userId, Long reviewId) {
        return ReviewLikeResponse.builder()
            .reviewId(reviewId)
            .likeCount(33)
            .message("리뷰에 좋아요를 추가했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public ReviewLikeResponse unlikeReview(Long userId, Long reviewId) {
        return ReviewLikeResponse.builder()
            .reviewId(reviewId)
            .likeCount(32)
            .message("리뷰 좋아요를 취소했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public List<ReviewSummaryResponse> getLikedReviews(Long userId) {
        return List.of(
            ReviewSummaryResponse.builder()
                .reviewId(3001L)
                .bookId(3L)
                .bookTitle("클린 아키텍처")
                .rating(5)
                .content("아주 인상적인 책")
                .likeCount(99)
                .liked(true)
                .createdAt(LocalDateTime.now().minusDays(3))
                .build()
        );
    }
}
