package io.github.shanastasia06.bookcatalog.repository;

import io.github.shanastasia06.bookcatalog.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    List<Author> findByFirstNameContainingIgnoreCase(String firstName);

    List<Author> findByLastNameContainingIgnoreCase(String lastName);

    // для проверки отсутствия дубликатов
    List<Author> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    boolean existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    List<Author> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    @Query("SELECT DISTINCT a FROM Author a " +
            "LEFT JOIN FETCH a.books " +
            "WHERE a.id = :id")
    Optional<Author> findByIdWithDetails(@Param("id") Long id);
}
