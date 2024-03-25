package com.cafe.com.cafe.management.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Categorydto {
    @NotEmpty(message = "id cannot be empty")
    @NotNull(message = "id cannot be null")
    private Integer id;
    private String categoryName;
}
