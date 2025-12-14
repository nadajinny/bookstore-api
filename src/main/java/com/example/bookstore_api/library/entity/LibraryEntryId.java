package com.example.bookstore_api.library.entity;

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
public class LibraryEntryId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "book_id")
    private Long bookId;
}
