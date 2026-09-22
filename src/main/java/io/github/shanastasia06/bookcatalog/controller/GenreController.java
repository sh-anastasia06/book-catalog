package io.github.shanastasia06.bookcatalog.controller;

import io.github.shanastasia06.bookcatalog.dto.GenreRequestDto;
import io.github.shanastasia06.bookcatalog.dto.GenreResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Genre;
import io.github.shanastasia06.bookcatalog.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> getAll(@RequestParam(required = false) String name) {
        List<GenreResponseDto> genres = genreService.findAll(name);
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDto> getById(@PathVariable Long id) {
        GenreResponseDto genre = genreService.findById(id);
        return ResponseEntity.ok(genre);
    }

    @PostMapping
    public ResponseEntity<GenreResponseDto> create(@Valid @RequestBody GenreRequestDto genreRequestDto) {
        GenreResponseDto createdGenre = genreService.create(genreRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody GenreRequestDto genreRequestDto
    ) {
        GenreResponseDto updatedGenre = genreService.update(id, genreRequestDto);
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
