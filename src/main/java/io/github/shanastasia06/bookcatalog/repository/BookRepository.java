package io.github.shanastasia06.bookcatalog.repository;

import io.github.shanastasia06.bookcatalog.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Book> findAllByPublisherId(Long publisherId);

    List<Book> findAllByAuthorsId(Long authorId);

    List<Book> findAllByGenresId(Long genreId);

    List<Book> findAllByTitleContainingIgnoreCase(String titleKeyword);

    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.publisher " +
            "LEFT JOIN FETCH b.authors " +
            "LEFT JOIN FETCH b.genres " +
            "WHERE b.id = :id")
    Optional<Book> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.publisher " +
            "LEFT JOIN FETCH b.authors " +
            "LEFT JOIN FETCH b.genres")
    List<Book> findAllWithDetails();
}
