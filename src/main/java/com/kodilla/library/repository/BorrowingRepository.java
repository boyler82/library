package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Borrowing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {
    List<Borrowing> findAll();
    Borrowing save(Borrowing borrowing);

    @Query("SELECT b FROM Borrowing b WHERE b.bookCopy = :bookCopy AND b.inDate IS NULL")
    Optional<Borrowing> findByBookCopyAndReturnDateIsNull(@Param("bookCopy") BookCopy bookCopy);
}
