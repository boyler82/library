package com.kodilla.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyDto {

    private Long id;
    private Long titleId;
    private BookStatus status;
    private List<Long> borrowingsId;
}