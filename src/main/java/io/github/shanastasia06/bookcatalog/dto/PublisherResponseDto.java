package io.github.shanastasia06.bookcatalog.dto;

import java.util.Set;

public record PublisherResponseDto(
        Long id,
        String name,
        String country
) { }
