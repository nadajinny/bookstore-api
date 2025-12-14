package com.example.bookstore_api.comment.service;

import com.example.bookstore_api.comment.dto.CommentCreateRequest;
import com.example.bookstore_api.comment.dto.CommentLikeResponse;
import com.example.bookstore_api.comment.dto.CommentResponse;
import com.example.bookstore_api.comment.dto.CommentUpdateRequest;
import com.example.bookstore_api.common.response.PageResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public CommentResponse createComment(Long userId, CommentCreateRequest request) {
        return CommentResponse.builder()
            .commentId(5001L)
            .reviewId(request.getReviewId())
            .authorName("사용자" + userId)
            .content(request.getContent())
            .likeCount(0)
            .liked(false)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public PageResponse<CommentResponse> getComments(
        Long reviewId,
        int page,
        int size,
        String sort,
        Long userId
    ) {
        List<CommentResponse> content = List.of(
            CommentResponse.builder()
                .commentId(5001L)
                .reviewId(reviewId)
                .authorName("댓글 작성자")
                .content("동의합니다")
                .likeCount(3)
                .liked(userId != null)
                .createdAt(LocalDateTime.now().minusHours(3))
                .updatedAt(LocalDateTime.now().minusHours(1))
                .build(),
            CommentResponse.builder()
                .commentId(5002L)
                .reviewId(reviewId)
                .authorName("또 다른 사용자")
                .content("좋은 리뷰네요")
                .likeCount(1)
                .liked(false)
                .createdAt(LocalDateTime.now().minusHours(6))
                .updatedAt(LocalDateTime.now().minusHours(6))
                .build()
        );
        return new PageResponse<>(content, page, size, content.size(), 1);
    }

    public CommentResponse updateComment(Long userId, Long commentId, CommentUpdateRequest request) {
        return CommentResponse.builder()
            .commentId(commentId)
            .reviewId(1001L)
            .authorName("사용자" + userId)
            .content(request.getContent() != null ? request.getContent() : "기존 내용")
            .likeCount(4)
            .liked(true)
            .createdAt(LocalDateTime.now().minusDays(1))
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public void deleteComment(Long userId, Long commentId) {
        // Soft Delete 로직이 들어갈 자리입니다.
    }

    public CommentLikeResponse likeComment(Long userId, Long commentId) {
        return CommentLikeResponse.builder()
            .commentId(commentId)
            .likeCount(12)
            .message("댓글에 좋아요를 추가했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public CommentLikeResponse unlikeComment(Long userId, Long commentId) {
        return CommentLikeResponse.builder()
            .commentId(commentId)
            .likeCount(11)
            .message("댓글 좋아요를 취소했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }
}
