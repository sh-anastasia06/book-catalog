package io.github.shanastasia06.bookcatalog.repository;

import io.github.shanastasia06.bookcatalog.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    // проверка отсутствия дубликатов
    Optional<Publisher> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Publisher> findByNameContainingIgnoreCase(String nameKeyword);

    List<Publisher> findByCountryContainingIgnoreCase(String countryKeyword);

    @Query("SELECT DISTINCT p FROM Publisher p " +
            "LEFT JOIN FETCH p.books " +
            "WHERE p.id = :id")
    Optional<Publisher> findByIdWithDetails(@Param("id") Long id);
}
