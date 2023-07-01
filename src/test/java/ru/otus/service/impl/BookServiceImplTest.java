package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Book service для работы с книгами должно")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private AuthorDao authorDao;

    private Genre expectedGenre;

    private Author expectedAuthor;

    private Book expectedBook;

    @BeforeEach
    public void init() {
        expectedGenre = new Genre(3L, "adventures");
        expectedAuthor = new Author(1L, "Jack London");
        expectedBook = new Book(6L, "Hearts of Three", expectedAuthor, expectedGenre);
    }

    @Test
    void shouldSaveBook() {
        var bookDto = new BookDto(6L, "Hearts of Three", "Jack London", "adventures");

        when(genreDao.getById(3L)).thenReturn(expectedGenre);
        when(authorDao.getByName("Jack London")).thenReturn(List.of(expectedAuthor));

        assertThatCode(() -> bookService.saveBook(bookDto))
                .doesNotThrowAnyException();

    }

    @Test
    void shouldUpdateBook() {
        var bookDto = new BookDto(6L, "Hearts of Three", "Jack London", "adventures");

        when(genreDao.getById(3L)).thenReturn(expectedGenre);
        when(authorDao.getByName("Jack London")).thenReturn(List.of(expectedAuthor));

        assertThatCode(() -> bookService.updateBook(bookDto))
                .doesNotThrowAnyException();
    }

    @Test
    void getBookById() {
        when(bookDao.getById(6L)).thenReturn(expectedBook);
        var actualBook = bookService.getBookById(6L);
        assertEquals(actualBook, expectedBook);
    }

    @Test
    void deleteBookById() {
        assertThatCode(() -> bookService.deleteBookById(6L))
                .doesNotThrowAnyException();
    }

    @Test
    void getBooks() {
        when(bookDao.getAll()).thenReturn(List.of(expectedBook));
        var actualBooks = bookService.getBooks();
        assertThat(actualBooks)
                .containsExactlyInAnyOrder(expectedBook);
    }

    @Test
    void countBooks() {
        when(bookDao.count()).thenReturn(44);
        var actualCount = bookService.countBooks();
        assertEquals(actualCount, 44);
    }
}