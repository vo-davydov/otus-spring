package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 3;

    private static final long EXPECTED_BOOK_ID = 4L;

    private static final long EXISTING_BOOK_ID = 1L;

    private static final String EXISTING_BOOK_NAME = "IT";

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String EXISTING_AUTHOR_NAME = "Stephen king";

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "horror";

    @Autowired
    private BookDao bookDaoJdbc;

    @Test
    void shouldReturnExpectedBookCount() {
        Integer actualBookCount = bookDaoJdbc.count();
        assertNotNull(actualBookCount);
        assertThat(actualBookCount).isEqualTo(EXPECTED_BOOK_COUNT);
    }

    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book(4L, "The Dark Tower", new Author(1L, "Stephen king"),
                new Genre(1L, "horror"));
        bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getById(EXPECTED_BOOK_ID);
        assertThat(expectedBook).usingRecursiveComparison().isEqualTo(actualBook);
    }

    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(1L, "The Dark Tower", new Author(1L, "Stephen king"),
                new Genre(1L, "horror"));
        bookDaoJdbc.update(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(expectedBook).usingRecursiveComparison().isEqualTo(actualBook);
    }

    @Test
    void shouldReturnExpectedBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
                new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME), new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertEquals(actualBook.getName(), expectedBook.getName());
        assertEquals(actualBook.getId(), expectedBook.getId());
        assertEquals(actualBook.getAuthor(), expectedBook.getAuthor());
        assertEquals(actualBook.getGenre(), expectedBook.getGenre());
    }

    @Test
    void shouldReturnExpectedBookList() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
                new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME), new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));

        Book expectedBook2 = new Book(2L, "The Black Swan: The Impact of the Highly Improbable",
                new Author(2L, "Nassim Taleb"), new Genre(2L, "non fiction"));

        Book expectedBook3 = new Book(3L, "Clean Code: A Handbook of Agile Software Craftsmanship",
                new Author(3L, "Robert Martin"), new Genre(3L, "programming"));

        List<Book> actualPersonList = bookDaoJdbc.getAll();
        assertThat(actualPersonList)
                .containsExactlyInAnyOrder(expectedBook, expectedBook2, expectedBook3);
    }

    @Test
    void shouldDeleteBook() {
        assertThatCode(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDaoJdbc.delete(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDaoJdbc.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}