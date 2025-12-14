package com.example.bookstore_api.user.dto;

import com.example.bookstore_api.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(max = 100)
    private String name;

    @Email
    @Size(max = 255)
    private String email;

    @Size(min = 8, max = 50)
    private String password;

    private UserRole role;
}
