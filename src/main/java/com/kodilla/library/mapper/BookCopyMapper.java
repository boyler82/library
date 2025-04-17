package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookCopyDto;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Title;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCopyMapper {

    private final BorrowingRepository borrowingRepository;
    private final TitleRepository titleRepository;

    public BookCopy mapToBookCopy(BookCopyDto bookCopyDto) {

        Title title = titleRepository.findById(bookCopyDto.getTitleId())
                .orElseThrow(() -> new RuntimeException("Title with ID " + bookCopyDto.getTitleId() + " not found"));

        List<Borrowing> borrowings = bookCopyDto.getBorrowingsId() != null
                ? bookCopyDto.getBorrowingsId().stream()
                .map(id -> borrowingRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Borrowing with ID " + id + " not found"))
                )
                .collect(Collectors.toList())
                : List.of();

        return new BookCopy(
                null,
                title,
                bookCopyDto.getStatus(),
                borrowings
        );
    }
    public BookCopyDto mapToBookCopyDto(BookCopy bookCopy) {
        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getTitle().getId(),
                bookCopy.getStatus(),
                bookCopy.getBorrowings().stream()
                        .map(borrowing -> borrowing.getId())
                        .collect(Collectors.toList())
        );
    }
}
