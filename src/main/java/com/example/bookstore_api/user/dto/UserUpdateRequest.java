package com.example.bookstore_api.user.dto;

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

    @Size(max = 20)
    private String phone;

    @Size(max = 255)
    private String address;
}
