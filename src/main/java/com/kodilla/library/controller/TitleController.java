package com.kodilla.library.controller;

import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.TitleDto;
import com.kodilla.library.mapper.TitleMapper;
import com.kodilla.library.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/title")
@RequiredArgsConstructor
public class TitleController {

    private final DbService service;
    private final TitleMapper titleMapper;


    @GetMapping
    public List<TitleDto> getTitles() {
        List<Title> titles = service.getAllTitles();
        return titles.stream()
                .map(title -> titleMapper.mapToTitleDto(title))
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TitleDto> createTitle(@RequestBody TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        Title savedTitle = service.saveTitle(title);
        return ResponseEntity.ok(titleMapper.mapToTitleDto(savedTitle));
    }
}
