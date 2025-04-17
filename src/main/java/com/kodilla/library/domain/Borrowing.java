package com.kodilla.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "BORROWINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_copy_id", nullable = false)
    @JsonBackReference
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    @JsonBackReference
    private Reader reader;

    @Column(name = "out_date", nullable = false)
    private LocalDate outDate;

    @Column(name = "in_date")
    private LocalDate inDate;
}