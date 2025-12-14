package com.example.bookstore_api.book.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {

    @Size(max = 200)
    private String title;

    private String description;

    @Size(max = 20)
    private String isbn;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private Integer stock;

    private List<String> authors;

    private List<String> categories;
}
