package com.kodilla.library.controller;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookCopyDto;
import com.kodilla.library.domain.BookStatus;
import com.kodilla.library.mapper.BookCopyMapper;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/bookCopies")
@RequiredArgsConstructor
public class BookCopyController {

    private final DbService service;
    private final BookCopyMapper bookCopyMapper;
    private final BookCopyService bookCopyService;

    @GetMapping
    public List<BookCopyDto> getBookCopies() {
        List<BookCopy> bookCopies = service.getAllBookCopies();
        return bookCopies.stream()
                .map(bookCopy -> bookCopyMapper.mapToBookCopyDto(bookCopy))
                .collect(Collectors.toList());
    }

    @GetMapping("/by-title/{titleId}")
    public ResponseEntity<List<BookCopyDto>> getBookCopiesByTitleId(@PathVariable Long titleId) {
        List<BookCopyDto> bookCopies = bookCopyService.getBookCopiesByTitleId(titleId);
        return ResponseEntity.ok(bookCopies);
    }

    @GetMapping("/count/{titleId}")
    public ResponseEntity<Long> getBookCopyCount(@PathVariable Long titleId) {
        Long count = bookCopyService.getBookCopyCountByTitleId(titleId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/available/{titleId}")
    public ResponseEntity<Long> getAvailableBookCopyCount(@PathVariable Long titleId) {
        Long count = bookCopyService.getBookCopyCountByAvailable(titleId);
        return ResponseEntity.ok(count);
    }

   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookCopyDto> createBookCopy(@RequestBody BookCopyDto bookCopyDto) {
        BookCopy bookCopy = bookCopyMapper.mapToBookCopy(bookCopyDto);
        BookCopy savedBookCopy = bookCopyService.saveBookCopy(bookCopy);
        return ResponseEntity.ok(bookCopyMapper.mapToBookCopyDto(savedBookCopy));
    }

    @PutMapping("/{bookCopyId}/status")
    public ResponseEntity<Void> updateBookCopyStatus(@PathVariable Long bookCopyId, @RequestBody Map<String, String> request) {
        try {
            BookStatus newStatus = BookStatus.valueOf(request.get("status").toUpperCase());
            bookCopyService.updateBookCopyStatus(bookCopyId, newStatus);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
