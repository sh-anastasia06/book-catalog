package io.github.shanastasia06.bookcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequestDto(
        @NotBlank(message = "Имя автора не может быть пустым")
        @Size(max = 100, message = "Имя не должно превышать 100 символов")
        String firstName,
        @NotBlank(message = "Фамилия автора не может быть пустым")
        @Size(max = 100, message = "Фамилия не должна превышать 100 символов")
        String lastName,
        String bio
) { }
