package com.kodilla.library.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "titles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_id", unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publication_date", nullable = false)
    private String publicationDate;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    private List<BookCopy> bookCopysList;

}