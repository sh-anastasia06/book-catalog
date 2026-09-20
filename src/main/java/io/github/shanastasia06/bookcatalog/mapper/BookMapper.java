package io.github.shanastasia06.bookcatalog.mapper;

import io.github.shanastasia06.bookcatalog.dto.BookRequestDto;
import io.github.shanastasia06.bookcatalog.dto.BookResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PublisherMapper.class, AuthorMapper.class, GenreMapper.class})
public interface BookMapper {
    // entity -> response dto
    BookResponseDto toDto(Book book);

    // request dto -> entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Book toEntity(BookRequestDto dto);
}
