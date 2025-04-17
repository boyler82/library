package com.kodilla.library.service;


import com.kodilla.library.domain.*;
import com.kodilla.library.mapper.BorrowingMapper;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BookCopyRepository bookCopyRepository;
    private final BorrowingRepository borrowingRepository;
    private final ReaderRepository readerRepository;
    private final BorrowingMapper borrowingMapper;

    public BorrowingDto borrowBook(Long bookCopyId, Long readerId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new RuntimeException("BookCopy with ID " + bookCopyId + " not found"));

        if (bookCopy.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("BookCopy with ID " + bookCopyId + " is not available for borrowing");
        }
        var reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new RuntimeException("Reader with ID " + readerId + " not found"));

        bookCopy.setStatus(BookStatus.BORROWED);
        bookCopyRepository.save(bookCopy);

        Borrowing borrowing = new Borrowing(
                null,
                bookCopy,
                reader,
                LocalDate.now(),
                null
        );

        Borrowing savedBorrowing = borrowingRepository.save(borrowing);

        return borrowingMapper.mapToBorrowingDto(savedBorrowing);
    }

    public BorrowingDto returnBook(Long bookCopyId) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new RuntimeException("BookCopy with ID " + bookCopyId + " not found"));

        if (bookCopy.getStatus() != BookStatus.BORROWED) {
            throw new RuntimeException("BookCopy with ID " + bookCopyId + " is not currently borrowed");
        }

        Borrowing borrowing = borrowingRepository.findByBookCopyAndReturnDateIsNull(bookCopy)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found for BookCopy ID " + bookCopyId));

        borrowing.setInDate(LocalDate.now());

        bookCopy.setStatus(BookStatus.AVAILABLE);
        bookCopyRepository.save(bookCopy);

        borrowingRepository.save(borrowing);

        return borrowingMapper.mapToBorrowingDto(borrowing);
    }
}