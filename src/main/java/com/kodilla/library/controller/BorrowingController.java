package com.kodilla.library.controller;

import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.BorrowingDto;
import com.kodilla.library.mapper.BorrowingMapper;
import com.kodilla.library.service.BorrowingService;
import com.kodilla.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;
    private final BorrowingMapper borrowingMapper;
    private final DbService dbService;

    @PostMapping()
    public ResponseEntity<BorrowingDto> borrowBook(@RequestBody Map<String, Long> request) {
        Long bookCopyId = request.get("bookCopyId");
        Long readerId = request.get("readerId");

        BorrowingDto borrowingDto = borrowingService.borrowBook(bookCopyId, readerId);
        return ResponseEntity.ok(borrowingDto);
    }

    @PutMapping("/return/{bookCopyId}")
    public ResponseEntity<BorrowingDto> returnBook(@PathVariable Long bookCopyId) {
        BorrowingDto borrowingDto = borrowingService.returnBook(bookCopyId);
        return ResponseEntity.ok(borrowingDto);
    }

    @GetMapping()
    public ResponseEntity<List<BorrowingDto>> getAllBorrowings() {
        List<Borrowing> borrowings = dbService.getAllBorrowings();
        List<BorrowingDto> borrowingDtos = borrowings.stream()
                .map(borrowing -> borrowingMapper.mapToBorrowingDto(borrowing))
                .collect(Collectors.toList());

        return ResponseEntity.ok(borrowingDtos);
    }

}
