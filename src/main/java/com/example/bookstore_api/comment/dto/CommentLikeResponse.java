package com.example.bookstore_api.comment.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentLikeResponse {

    private Long commentId;
    private long likeCount;
    private String message;
    private LocalDateTime occurredAt;
}
