package io.github.shanastasia06.bookcatalog.service;

import io.github.shanastasia06.bookcatalog.dto.BookRequestDto;
import io.github.shanastasia06.bookcatalog.dto.BookResponseDto;
import io.github.shanastasia06.bookcatalog.entity.Author;
import io.github.shanastasia06.bookcatalog.entity.Book;
import io.github.shanastasia06.bookcatalog.entity.Genre;
import io.github.shanastasia06.bookcatalog.entity.Publisher;
import io.github.shanastasia06.bookcatalog.exception.EntityAlreadyExistsException;
import io.github.shanastasia06.bookcatalog.exception.EntityNotFoundException;
import io.github.shanastasia06.bookcatalog.mapper.BookMapper;
import io.github.shanastasia06.bookcatalog.repository.AuthorRepository;
import io.github.shanastasia06.bookcatalog.repository.BookRepository;
import io.github.shanastasia06.bookcatalog.repository.GenreRepository;
import io.github.shanastasia06.bookcatalog.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    public List<BookResponseDto> findAll(Long authorId, Long publisherId, Long genreId, String titleKeyword) {
        List<Book> books; // от db получаем entity

        if (authorId!= null) {
            books = bookRepository.findAllByAuthorsId(authorId);
        } else if (publisherId!= null) {
            books = bookRepository.findAllByPublisherId(publisherId);
        } else if (genreId!= null) {
            books = bookRepository.findAllByGenresId(genreId);
        } else if (titleKeyword != null && !titleKeyword.isBlank()) {
            books = bookRepository.findAllByTitleContainingIgnoreCase(titleKeyword.trim());
        } else {
            books = bookRepository.findAllWithDetails();
        }

        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с ID " + id + " не найдена"));
        return bookMapper.toDto(book);
    }

    @Transactional
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        if (bookRequestDto.isbn() != null && bookRepository.existsByIsbn(bookRequestDto.isbn())) {
            throw new EntityAlreadyExistsException("Книга с ISBN " + bookRequestDto.isbn() + " уже существует");
        }

        Book book = bookMapper.toEntity(bookRequestDto);

        if (bookRequestDto.publisherId() != null) {
            Publisher publisher = publisherRepository.findById(bookRequestDto.publisherId())
                    .orElseThrow(() -> new EntityNotFoundException("Издатель с ID " + bookRequestDto.publisherId() + " не найден"));
            book.setPublisher(publisher);
        }

        if (bookRequestDto.authorsIds() != null && !bookRequestDto.authorsIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(bookRequestDto.authorsIds());
            if (authors.size() != bookRequestDto.authorsIds().size()) {
                throw new EntityNotFoundException("Один или несколько авторов не найдены");
            }
            book.setAuthors(new HashSet<>(authors));
        }

        if (bookRequestDto.genresIds() != null && !bookRequestDto.genresIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(bookRequestDto.genresIds());
            if (genres.size() != bookRequestDto.genresIds().size()) {
                throw new EntityNotFoundException("Один или несколько жанров не найдены");
            }
            book.setGenres(new HashSet<>(genres));
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Transactional
    public BookResponseDto update(Long id, BookRequestDto bookRequestDto) {
        Book existingBook = bookRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Книга с ID " + id + " не найдена"));

        if  (bookRequestDto.isbn() != null &&
            !bookRequestDto.isbn().equals(existingBook.getIsbn()) &&
            bookRepository.existsByIsbn(bookRequestDto.isbn())) {
            throw new EntityAlreadyExistsException("Книга с ISBN " + bookRequestDto.isbn() + " уже существует");
        }

        existingBook.setTitle(bookRequestDto.title());
        existingBook.setIsbn(bookRequestDto.isbn());
        existingBook.setPublicationYear(bookRequestDto.publicationYear());
        existingBook.setPageCount(bookRequestDto.pageCount());
        existingBook.setDescription(bookRequestDto.description());

        if (bookRequestDto.publisherId() != null) {
            Publisher publisher = publisherRepository.findById(bookRequestDto.publisherId())
                    .orElseThrow(() -> new EntityNotFoundException("Издатель с ID " + bookRequestDto.publisherId() + " не найден"));
            existingBook.setPublisher(publisher);
        } else {
            existingBook.setPublisher(null);
        }

        if (bookRequestDto.authorsIds() != null) {
            if (bookRequestDto.authorsIds().isEmpty()) {
                existingBook.getAuthors().clear();
            } else {
                List<Author> authors = authorRepository.findAllById(bookRequestDto.authorsIds());
                if (authors.size() != bookRequestDto.authorsIds().size()) {
                    throw new EntityNotFoundException("Один или несколько авторов не найдены");
                }
                existingBook.getAuthors().clear();
                existingBook.getAuthors().addAll(authors);
            }
        }

        if (bookRequestDto.genresIds() != null) {
            if (bookRequestDto.genresIds().isEmpty()) {
                existingBook.getGenres().clear();
            } else {
                List<Genre> genres = genreRepository.findAllById(bookRequestDto.genresIds());
                if (genres.size() != bookRequestDto.genresIds().size()) {
                    throw new EntityNotFoundException("Один или несколько жанров не найдены");
                }
                existingBook.getGenres().clear();
                existingBook.getGenres().addAll(genres);
            }
        }

        return bookMapper.toDto(bookRepository.save(existingBook));
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Книга с ID " + id + " не найдена");
        }
        bookRepository.deleteById(id);
    }
}
