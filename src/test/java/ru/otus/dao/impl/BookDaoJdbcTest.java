package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXPECTED_BOOK_COUNT = 1;

    private static final long EXISTING_BOOK_ID = 1L;

    private static final String EXISTING_BOOK_NAME = "White Fang";

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String EXISTING_AUTHOR_NAME = "Jack London";

    private static final long EXISTING_GENRE_ID = 3L;

    private static final String EXISTING_GENRE_NAME = "adventures";

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
        Book expectedBook = new Book(6L, "Hearts of Three", new Author(1L, "Jack London"),
                new Genre(3L, "adventures"));
        bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBook() {
        Book expectedBook = new Book(1L, "Hearts of Three", new Author(1L, "Jack London"),
                new Genre(3L, "adventures"));
        bookDaoJdbc.update(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
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

        List<Book> actualPersonList = bookDaoJdbc.getAll();
        assertThat(actualPersonList)
                .containsExactlyInAnyOrder(expectedBook);
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