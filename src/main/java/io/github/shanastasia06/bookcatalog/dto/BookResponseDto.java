package io.github.shanastasia06.bookcatalog.dto;

import java.util.Set;

public record BookResponseDto(
        Long id,
        String title,
        String isbn,
        Integer publicationYear,
        Integer pageCount,
        String description,

        PublisherResponseDto publisher,
        Set<AuthorResponseDto> authors,
        Set<GenreResponseDto> genres
) { }
