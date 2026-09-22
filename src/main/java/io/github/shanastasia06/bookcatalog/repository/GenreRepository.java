package io.github.shanastasia06.bookcatalog.repository;

import io.github.shanastasia06.bookcatalog.entity.Author;
import io.github.shanastasia06.bookcatalog.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    // проверка отсутствия дубликатов
    Optional<Genre> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Genre> findByNameContainingIgnoreCase(String nameKeyword);

    @Query("SELECT DISTINCT g FROM Genre g " +
            "LEFT JOIN FETCH g.books " +
            "WHERE g.id = :id")
    Optional<Genre> findByIdWithDetails(@Param("id") Long id);
}
