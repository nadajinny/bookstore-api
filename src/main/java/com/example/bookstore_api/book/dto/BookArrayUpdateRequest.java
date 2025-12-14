package com.example.bookstore_api.book.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookArrayUpdateRequest {

    @Size(max = 20)
    private List<String> authorsToAdd;

    @Size(max = 20)
    private List<String> authorsToRemove;

    @Size(max = 20)
    private List<String> authorsReplace;

    @Size(max = 20)
    private List<String> categoriesToAdd;

    @Size(max = 20)
    private List<String> categoriesToRemove;

    @Size(max = 20)
    private List<String> categoriesReplace;
}
