package com.kodilla.library.domain;

import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "BOOK_COPIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_copy_id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;

    @OneToMany(mappedBy = "bookCopy", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;
}