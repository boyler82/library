package com.kodilla.library.service;

import com.kodilla.library.domain.*;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import com.kodilla.library.repository.TitleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DbService {

    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;
    private final BorrowingRepository borrowingRepository;
    private final BookCopyRepository bookCopyRepository;

    public List<Reader> getAllReaders(){
        return readerRepository.findAll();
    }

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll(); }

    public List<BookCopy> getBookCopiesByTitleId(Long titleId) {
        return bookCopyRepository.findByTitleId(titleId); }


    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll(); }

    public Reader saveReader(Reader reader) {
        return readerRepository.save(reader);
    }
    public BookCopy saveBookCopy(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }
    public Title saveTitle(Title title) {
        return titleRepository.findByTitleAndAuthor(title.getTitle(), title.getAuthor())
                .orElseGet(() -> titleRepository.save(title));
    }

    public List<Title> getAllTitles() {
        return titleRepository.findAll(); }
    public Borrowing saveBorrowing(Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

}

