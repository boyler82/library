package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingDto {

    private Long Id;
    private Long bookCopyId;
    private Long readerId;
    private LocalDate outDate;
    private LocalDate inDate;
}
