package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    private Long id;

    private String name;

    private Author author;

    private Genre genre;

    public Book(String name, Author author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Long getAuthorId() {
        if (author != null) {
            return author.getId();
        }

        return null;
    }

    public Long getGenreId() {
        if (genre != null) {
            return genre.getId();
        }

        return null;
    }

}