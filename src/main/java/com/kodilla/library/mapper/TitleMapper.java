package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.TitleDto;
import com.kodilla.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleMapper {

    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public TitleMapper(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }


    public Title mapToTitle(TitleDto titleDto) {
        List<BookCopy> bookCopies = titleDto.getBookCopiesId() != null
                ? titleDto.getBookCopiesId().stream()
                .map(id -> bookCopyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("BookCopy with ID " + id + " not found")))
                .collect(Collectors.toList())
                : List.of();

        return new Title(
                null,
                titleDto.getTitle(),
                titleDto.getAuthor(),
                titleDto.getPublicationDate(),
                bookCopies
        );
    }

    public TitleDto mapToTitleDto(Title title) {
        return new TitleDto(
                title.getId(),
                title.getTitle(),
                title.getAuthor(),
                title.getPublicationDate(),
                title.getBookCopysList().stream()
                        .map(bookCopy -> bookCopy.getId())
                        .collect(Collectors.toList())
        );
    }

}
