package io.github.shanastasia06.bookcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GenreRequestDto(
        @NotBlank(message = "Название жанра не может быть пустым")
        @Size(max = 100, message = "Название жанра не должно превышать 100 символов")
        String name
) { }
