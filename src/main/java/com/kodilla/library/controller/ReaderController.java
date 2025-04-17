package com.kodilla.library.controller;

import com.kodilla.library.domain.Reader;
import com.kodilla.library.domain.ReaderDto;
import com.kodilla.library.mapper.ReaderMapper;
import com.kodilla.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/readers")
@RequiredArgsConstructor
public class ReaderController {

    private final DbService service;
    private final ReaderMapper readerMapper;


    @GetMapping
    public List<ReaderDto> getReaders() {
        List<Reader> readers = service.getAllReaders();
        return readers.stream()
                .map(readerMapper::mapToReaderDto)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReaderDto> createReader(@RequestBody ReaderDto readerDto) {
        Reader reader = readerMapper.mapToReader(readerDto);
        Reader savedReader = service.saveReader(reader);
        return ResponseEntity.ok(readerMapper.mapToReaderDto(savedReader));
    }
}
