package com.kodilla.library.service;

import com.kodilla.library.domain.*;
import com.kodilla.library.mapper.BorrowingMapper;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {


    @Mock
    private BookCopyRepository bookCopyRepository;
    @Mock
    private BorrowingRepository borrowingRepository;
    @Mock
    private ReaderRepository readerRepository;
    @Mock
    private BorrowingMapper borrowingMapper;

    @InjectMocks
    private BorrowingService borrowingService;

    @Test
    void shouldBorrowBookifBookIsAvailable() {
//          WHEN
        Long bookCopyId = 1L;
        Long readerId = 1L;

        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(bookCopyId);
        bookCopy.setStatus(BookStatus.AVAILABLE);

        Reader reader = new Reader();
        reader.setId(readerId);

        Borrowing borrowing = new Borrowing();
        borrowing.setBookCopy(bookCopy);
        borrowing.setReader(reader);
        borrowing.setOutDate(LocalDate.now());

        BorrowingDto borrowingDto = new BorrowingDto();

        when(bookCopyRepository.findById(bookCopyId)).thenReturn(Optional.of(bookCopy));
        when(readerRepository.findById(readerId)).thenReturn(Optional.of(reader));
        when(borrowingRepository.save(any(Borrowing.class))).thenReturn(borrowing);
        when(borrowingMapper.mapToBorrowingDto(any(Borrowing.class))).thenReturn(borrowingDto);

        // WHEN
        BorrowingDto result = borrowingService.borrowBook(bookCopyId, readerId);

        // THEN
        assertNotNull(result);
        verify(bookCopyRepository, times(1)).save(any(BookCopy.class));
        verify(borrowingRepository, times(1)).save(any(Borrowing.class));
        verify(borrowingMapper, times(1)).mapToBorrowingDto(any(Borrowing.class));
    }

    @Test
    void shouldReturnBook() {  }


}