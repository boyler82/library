package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {
    List<BookCopy> findAll();

    @Query("SELECT b FROM BookCopy b WHERE b.title.id = :titleId")
    List<BookCopy> findByTitleId(@Param("titleId") Long titleId);

    @Query("SELECT COUNT(b) FROM BookCopy b WHERE b.title.id = :titleId AND b.status = 'AVAILABLE'")
    Long countAvailableByTitleId(@Param("titleId") Long titleId);

    @Query("SELECT COUNT(b) FROM BookCopy b WHERE b.title.id = :titleId")
    Long countByTitleId(@Param("titleId") Long titleId);

    BookCopy save(BookCopy bookCopy);
}
