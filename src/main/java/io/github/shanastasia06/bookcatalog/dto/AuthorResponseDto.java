package io.github.shanastasia06.bookcatalog.dto;

import java.util.Set;

public record AuthorResponseDto(
        Long id,
        String firstName,
        String lastName,
        String bio
) { }
