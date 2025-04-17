package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookCopyDto;
import com.kodilla.library.domain.BookStatus;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.repository.BookCopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookCopyMapper bookCopyMapper;

    public BookCopy saveBookCopy(BookCopy bookCopy) {
        if (bookCopy.getStatus() == null) {
            bookCopy.setStatus(BookStatus.AVAILABLE);
        }
        return bookCopyRepository.save(bookCopy);
    }

    public List<BookCopyDto> getBookCopiesByTitleId(Long titleId) {
        List<BookCopy> bookCopies = bookCopyRepository.findByTitleId(titleId);
        return bookCopies.stream()
                .map(bookCopy -> bookCopyMapper.mapToBookCopyDto(bookCopy))
                .collect(Collectors.toList());
    }

    public void updateBookCopyStatus(Long bookCopyId, BookStatus newStatus) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new RuntimeException("BookCopy with ID " + bookCopyId + " not found"));

        if (bookCopy.getStatus() == newStatus) {
            throw new RuntimeException("BookCopy already has status " + newStatus);
        }

        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);
    }

    public Long getBookCopyCountByTitleId(Long titleId) {
        return bookCopyRepository.countByTitleId(titleId);
    }

    public Long getBookCopyCountByAvailable(Long titleId) {
        return bookCopyRepository.countAvailableByTitleId(titleId);
    }
}
