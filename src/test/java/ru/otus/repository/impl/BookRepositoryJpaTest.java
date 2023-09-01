package ru.otus.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Jpa-based repository for working with book")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    private static final long EXPECTED_BOOK_COUNT = 3L;

    private static final long EXPECTED_BOOK_ID = 4L;

    @Test
    void count() {
        Long actualBookCount = repositoryJpa.count();
        assertNotNull(actualBookCount);
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    void save() {
        Book expectedBook = new Book("Hearts of Three", null,
                null, List.of());
        repositoryJpa.save(expectedBook);
        Optional<Book> actualBook = repositoryJpa.findById(EXPECTED_BOOK_ID);
        assertNotNull(actualBook.get());
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findById() {
        Book expectedBook = new Book(1L, "IT",
                new Author(1L, "Stephen king"), new Genre(1L, "horror"),
                List.of(new Comment(1L, "BAD BAD", 1L)));
        Optional<Book> actualBook = repositoryJpa.findById(1L);
        assertNotNull(actualBook.get());
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findAll() {
        Book firstExpectedBook = new Book(1L, "IT",
                new Author(1L, "Stephen king"), new Genre(1L, "horror"),
                List.of(new Comment(1L, "BAD BAD", 1L)));
        Book secondExpectedBook = new Book(2L, "The Black Swan: The Impact of the Highly Improbable",
                new Author(2L, "Nassim Taleb"), new Genre(2L, "non fiction"), List.of());
        Book thirdExpectedBook = new Book(3L, "Clean Code: A Handbook of Agile Software Craftsmanship",
                new Author(3L, "Robert Martin"), new Genre(3L, "programming"), List.of());
        var expectedBooks = List.of(firstExpectedBook, secondExpectedBook, thirdExpectedBook);
        var actualBooks = repositoryJpa.findAll();

        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    void delete() {
        assertThatCode(() -> repositoryJpa.delete(1L))
                .doesNotThrowAnyException();
    }

    @Test
    void findBooksByAuthor() {
        Book expectedBook = new Book(1L, "IT",
                new Author(1L, "Stephen king"), new Genre(1L, "horror"),
                List.of(new Comment(1L, "BAD BAD", 1L)));
        var books = repositoryJpa.findBooksByAuthor("Stephen king");
        assertEquals(1, books.size());
        assertNotNull(books.get(0));
        var actualBook = books.get(0);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void findBooksByName() {
        Book expectedBook = new Book(1L, "IT",
                new Author(1L, "Stephen king"), new Genre(1L, "horror"),
                List.of(new Comment(1L, "BAD BAD", 1L)));
        var books = repositoryJpa.findBooksByName("IT");
        assertEquals(1, books.size());
        assertNotNull(books.get(0));
        var actualBook = books.get(0);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}