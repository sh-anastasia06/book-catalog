package io.github.shanastasia06.bookcatalog.controller;

import io.github.shanastasia06.bookcatalog.dto.AuthorRequestDto;
import io.github.shanastasia06.bookcatalog.dto.AuthorResponseDto;
import io.github.shanastasia06.bookcatalog.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAll(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        List<AuthorResponseDto> authors = authorService.findAll(firstName, lastName);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable Long id) {
        AuthorResponseDto author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> create(@Valid @RequestBody AuthorRequestDto authorRequestDto) {
        AuthorResponseDto createdAuthor = authorService.create(authorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(
            @PathVariable Long id, @Valid @RequestBody AuthorRequestDto authorRequestDto
    ) {
        AuthorResponseDto updatedAuthor = authorService.update(id, authorRequestDto);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
