package io.github.shanastasia06.bookcatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublisherRequestDto(
        @NotBlank(message = "Название издательства не может быть пустым")
        @Size(max = 255, message = "Название не должно превышать 255 символов")
        String name,

        @Size(max = 100, message = "Название страны не должно превышать 100 символов")
        String country
) { }
