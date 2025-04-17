package com.kodilla.library.domain;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {

    private Long id;
    private String title;
    private String author;
    private String publicationDate;
    private List<Long> bookCopiesId;
}
