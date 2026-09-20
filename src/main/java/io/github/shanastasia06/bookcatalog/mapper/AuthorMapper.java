package io.github.shanastasia06.bookcatalog.mapper;

import io.github.shanastasia06.bookcatalog.dto.AuthorRequestDto;
import io.github.shanastasia06.bookcatalog.dto.AuthorResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    // entity -> response dto
    AuthorResponseDto toDto(Author author);

    // request dto -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorRequestDto dto);
}
