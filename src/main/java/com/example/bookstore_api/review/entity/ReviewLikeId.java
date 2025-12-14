package com.example.bookstore_api.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewLikeId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "review_id")
    private Long reviewId;
}
