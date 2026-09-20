package io.github.shanastasia06.bookcatalog.mapper;

import io.github.shanastasia06.bookcatalog.dto.GenreRequestDto;
import io.github.shanastasia06.bookcatalog.dto.GenreResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    // entity -> response dto
    GenreResponseDto toDto(Genre genre);

    // request dto -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreRequestDto dto);
}
