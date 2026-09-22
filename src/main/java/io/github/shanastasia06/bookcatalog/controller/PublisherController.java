package io.github.shanastasia06.bookcatalog.controller;

import io.github.shanastasia06.bookcatalog.dto.PublisherRequestDto;
import io.github.shanastasia06.bookcatalog.dto.PublisherResponseDto;
import io.github.shanastasia06.bookcatalog.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherResponseDto>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String country
    ) {
        List<PublisherResponseDto> publishers = publisherService.findAll(name, country);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDto> getById(@PathVariable Long id) {
        PublisherResponseDto publisher = publisherService.findById(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDto> create(@Valid @RequestBody PublisherRequestDto publisherRequestDto) {
        PublisherResponseDto createdPublisher = publisherService.create(publisherRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PublisherRequestDto publisherRequestDto
    ) {
        PublisherResponseDto updatedPublisher = publisherService.update(id, publisherRequestDto);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
