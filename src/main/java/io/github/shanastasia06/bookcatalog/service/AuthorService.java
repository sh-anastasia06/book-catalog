package io.github.shanastasia06.bookcatalog.service;

import io.github.shanastasia06.bookcatalog.dto.AuthorRequestDto;
import io.github.shanastasia06.bookcatalog.dto.AuthorResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Author;
import io.github.shanastasia06.bookcatalog.mapper.AuthorMapper;
import io.github.shanastasia06.bookcatalog.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorResponseDto> findAll(String firstName, String lastName) {
        List<Author> authors;

        boolean hasFirstName = firstName != null && !firstName.isBlank();
        boolean hasLastName = lastName != null && !lastName.isBlank();

        if (hasFirstName && hasLastName) {
            authors = authorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName.trim(), lastName.trim());
        } else if (hasFirstName) {
            authors = authorRepository.findByFirstNameContainingIgnoreCase(firstName.trim());
        } else if (hasLastName) {
            authors = authorRepository.findByLastNameContainingIgnoreCase(lastName.trim());
        } else {
            authors = authorRepository.findAll();
        }

        return authors.stream()
                .map(authorMapper::toDto)
                .toList();
    }

    public AuthorResponseDto findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор с ID " + id + " не найден"));
        return authorMapper.toDto(author);
    }

    @Transactional
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.toEntity(authorRequestDto);
        Author savedAuthor =  authorRepository.save(author);
        return authorMapper.toDto(savedAuthor);
    }

    @Transactional
    public AuthorResponseDto update(Long id, AuthorRequestDto authorRequestDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автор с ID " + id + " не найден"));

        existingAuthor.setFirstName(authorRequestDto.firstName());
        existingAuthor.setLastName(authorRequestDto.lastName());
        existingAuthor.setBio(authorRequestDto.bio());

        return authorMapper.toDto(authorRepository.save(existingAuthor));
    }

    @Transactional
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Автор с ID " + id + " не найден");
        }
        authorRepository.deleteById(id);
    }
}
