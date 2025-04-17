package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.BorrowingDto;
import com.kodilla.library.domain.Reader;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BorrowingMapper {

    private final ReaderRepository readerRepository;
    private final BookCopyRepository bookCopyRepository;

    public Borrowing mapToBorrowing(BorrowingDto borrowingDto) {
        BookCopy bookCopy = bookCopyRepository.findById(borrowingDto.getBookCopyId())
                .orElseThrow(() -> new RuntimeException("BookCopy with ID " + borrowingDto.getBookCopyId() + " not found"));

        Reader reader = readerRepository.findById(borrowingDto.getReaderId())
                .orElseThrow(() -> new RuntimeException("Reader with ID " + borrowingDto.getReaderId() + " not found"));
        return new Borrowing(
                null,
                bookCopy,
                reader,
                borrowingDto.getOutDate(),
                borrowingDto.getInDate()
        );
    }


    public BorrowingDto mapToBorrowingDto(Borrowing borrowing){
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getBookCopy().getId(),
                borrowing.getReader().getId(),
                borrowing.getOutDate(),
                borrowing.getInDate()
        );
    }
}
