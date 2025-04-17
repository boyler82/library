package com.kodilla.library.mapper;

import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.ReaderDto;
import com.kodilla.library.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderMapper {

    private final BorrowingRepository borrowingRepository;

    @Autowired
    public ReaderMapper(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public Reader mapToReader(ReaderDto readerDto) {
        List<Borrowing> borrowings = readerDto.getBorrowingsId() != null
                ? readerDto.getBorrowingsId().stream()
                .map(id -> borrowingRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Borrowing with ID " + id + " not found"))
                )
                .collect(Collectors.toList())
                : List.of();

        return new Reader(
                null,
                readerDto.getFirstName(),
                readerDto.getLastName(),
                readerDto.getAddress(),
                readerDto.getMail(),
                null,
                borrowings
        );
    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getAddress(),
                reader.getMail(),
                reader.getCreatedAt(),
                reader.getBorrowingsList().stream()
                        .map(Borrowing::getId)
                        .collect(Collectors.toList())
        );
    }

}
