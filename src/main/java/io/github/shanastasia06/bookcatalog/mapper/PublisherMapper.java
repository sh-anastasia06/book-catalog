package io.github.shanastasia06.bookcatalog.mapper;

import io.github.shanastasia06.bookcatalog.dto.PublisherResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    // entity -> response dto
    PublisherResponseDto toDto(Publisher publisher);

    // request dto -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Publisher toEntity(PublisherResponseDto dto);
}
