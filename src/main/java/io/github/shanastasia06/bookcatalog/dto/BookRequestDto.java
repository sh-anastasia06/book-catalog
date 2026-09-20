package io.github.shanastasia06.bookcatalog.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record BookRequestDto(
        @NotBlank(message = "Название книги не может быть пустым")
        String title,
        String isbn,
        Integer publicationYear,
        Integer pageCount,
        String description,

        Long publisherId,
        Set<Long> authorsIds,
        Set<Long> genresIds
) { }
