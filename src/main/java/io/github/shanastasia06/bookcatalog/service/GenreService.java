package io.github.shanastasia06.bookcatalog.service;

import io.github.shanastasia06.bookcatalog.dto.GenreRequestDto;
import io.github.shanastasia06.bookcatalog.dto.GenreResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Genre;
import io.github.shanastasia06.bookcatalog.mapper.GenreMapper;
import io.github.shanastasia06.bookcatalog.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreResponseDto> findAll(String nameKeyword) {
        List<Genre> genres;

        if (nameKeyword != null && !nameKeyword.isBlank()) {
            genres = genreRepository.findByNameContainingIgnoreCase(nameKeyword.trim());
        } else {
            genres = genreRepository.findAll();
        }

        return genres.stream()
                .map(genreMapper::toDto)
                .toList();
    }

    public GenreResponseDto findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Жанр с ID " + id + " не найден"));
        return genreMapper.toDto(genre);
    }

    @Transactional
    public GenreResponseDto create(GenreRequestDto genreRequestDto) {
        if (genreRepository.existsByNameIgnoreCase(genreRequestDto.name())) {
            throw new RuntimeException("Жанр '" + genreRequestDto.name() + "' уже существует");
        }
        Genre genre = genreMapper.toEntity(genreRequestDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional
    public GenreResponseDto update(Long id, GenreRequestDto genreRequestDto) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Жанр с ID " + id + " не найден"));

        if (!existingGenre.getName().equalsIgnoreCase(genreRequestDto.name())
                && genreRepository.existsByNameIgnoreCase(genreRequestDto.name())) {
            throw new RuntimeException("Жанр '" + genreRequestDto.name() + "' уже существует");
        }

        existingGenre.setName(genreRequestDto.name());

        return genreMapper.toDto(genreRepository.save(existingGenre));
    }

    @Transactional
    public void delete(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new RuntimeException("Жанр с ID " + id + " не найден");
        }
        genreRepository.deleteById(id);
    }
}
