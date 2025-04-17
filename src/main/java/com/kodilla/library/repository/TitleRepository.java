package com.kodilla.library.repository;

import com.kodilla.library.domain.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TitleRepository extends CrudRepository<Title, Long> {
    List<Title> findAll();
    Title save(Title title);
    @Query("SELECT t FROM Title t WHERE t.title = :title AND t.author = :author")
    Optional<Title> findByTitleAndAuthor(@Param("title") String title, @Param("author") String author);
}
