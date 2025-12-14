package com.example.bookstore_api.user.dto;

import com.example.bookstore_api.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
    private String email;
    private String name;
    private UserRole role;
}
