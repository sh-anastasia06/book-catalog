package io.github.shanastasia06.bookcatalog.controller;

import io.github.shanastasia06.bookcatalog.dto.BookRequestDto;
import io.github.shanastasia06.bookcatalog.dto.BookResponseDto;
import io.github.shanastasia06.bookcatalog.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long publisherId,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) String title
    ) {
        List<BookResponseDto> books = bookService.findAll(authorId, publisherId, genreId, title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getById(@PathVariable Long id) {
        BookResponseDto book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> save(@Valid @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto cratedBook = bookService.create(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cratedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto bookRequestDto
    ) {
        BookResponseDto updatedBook = bookService.update(id, bookRequestDto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
