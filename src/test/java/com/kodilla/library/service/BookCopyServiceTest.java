package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookCopyDto;
import com.kodilla.library.domain.BookStatus;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.repository.BookCopyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookCopyServiceTest {

    @Mock
    private BookCopyRepository bookCopyRepository;

    @Mock
    private BookCopyMapper bookCopyMapper;

    @InjectMocks
    private BookCopyService bookCopyService;

    @Test
    void shouldSaveBookCopyWithDefaultStatus() {
        // Given
        BookCopy bookCopy = new BookCopy();
        bookCopy.setStatus(null); // Status nie ustawiony

        BookCopy savedBookCopy = new BookCopy();
        savedBookCopy.setStatus(BookStatus.AVAILABLE);

        when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(savedBookCopy);

        // When
        BookCopy result = bookCopyService.saveBookCopy(bookCopy);

        // Then
        assertNotNull(result);
        assertEquals(BookStatus.AVAILABLE, result.getStatus());
        verify(bookCopyRepository, times(1)).save(any(BookCopy.class));
    }

    @Test
    void shouldGetBookCopiesByTitleId() {
        // Given
        Long titleId = 1L;
        List<BookCopy> bookCopies = List.of(new BookCopy(), new BookCopy());

        when(bookCopyRepository.findByTitleId(titleId)).thenReturn(bookCopies);
        when(bookCopyMapper.mapToBookCopyDto(any(BookCopy.class))).thenReturn(new BookCopyDto());

        // When
        List<BookCopyDto> result = bookCopyService.getBookCopiesByTitleId(titleId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookCopyRepository, times(1)).findByTitleId(titleId);
    }

    @Test
    void shouldUpdateBookCopyStatus() {
        // Given
        Long bookCopyId = 1L;
        BookCopy bookCopy = new BookCopy();
        bookCopy.setStatus(BookStatus.AVAILABLE);

        when(bookCopyRepository.findById(bookCopyId)).thenReturn(Optional.of(bookCopy));
        when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(bookCopy);

        // When
        bookCopyService.updateBookCopyStatus(bookCopyId, BookStatus.BORROWED);

        // Then
        assertEquals(BookStatus.BORROWED, bookCopy.getStatus());
        verify(bookCopyRepository, times(1)).save(bookCopy);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingToSameStatus() {
        // Given
        Long bookCopyId = 1L;
        BookCopy bookCopy = new BookCopy();
        bookCopy.setStatus(BookStatus.AVAILABLE);

        when(bookCopyRepository.findById(bookCopyId)).thenReturn(Optional.of(bookCopy));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () ->
                bookCopyService.updateBookCopyStatus(bookCopyId, BookStatus.AVAILABLE));

        assertEquals("BookCopy already has status AVAILABLE", exception.getMessage());
    }

    @Test
    void shouldGetBookCopyCountByTitleId() {
        // Given
        Long titleId = 1L;
        when(bookCopyRepository.countByTitleId(titleId)).thenReturn(5L);

        // When
        Long count = bookCopyService.getBookCopyCountByTitleId(titleId);

        // Then
        assertEquals(5L, count);
        verify(bookCopyRepository, times(1)).countByTitleId(titleId);
    }

    @Test
    void shouldGetAvailableBookCopyCountByTitleId() {
        // Given
        Long titleId = 1L;
        when(bookCopyRepository.countAvailableByTitleId(titleId)).thenReturn(3L);

        // When
        Long count = bookCopyService.getBookCopyCountByAvailable(titleId);

        // Then
        assertEquals(3L, count);
        verify(bookCopyRepository, times(1)).countAvailableByTitleId(titleId);
    }
}