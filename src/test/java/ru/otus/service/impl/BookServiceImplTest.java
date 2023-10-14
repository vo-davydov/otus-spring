package ru.otus.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DisplayName("Book service для работы с книгами должно ")
@SpringBootTest
class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private AuthorRepository authorRepository;

    private Genre expectedGenre;

    private Author expectedAuthor;

    private Book expectedBook;

    @BeforeEach
    public void init() {
        expectedGenre = new Genre(1L, "horror");
        expectedAuthor = new Author(1L, "Stephen king");
        expectedBook = new Book(1L, "IT", expectedAuthor, expectedGenre);
    }

    @Test
    void shouldSaveBook() {
        var bookDto = new BookDto(4L, "The Dark Tower", "Stephen king", "horror");

        when(genreRepository.findById(1L)).thenReturn(Optional.of(expectedGenre));
        when(authorRepository.findByNameIgnoreCase("Stephen king")).thenReturn(List.of(expectedAuthor));

        assertThatCode(() -> bookService.saveBook(bookDto))
                .doesNotThrowAnyException();

    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(expectedBook));
        var actualBook = bookService.getBookById(1L);
        assertNotNull(actualBook);
        assertEquals(actualBook, expectedBook);
    }

    @Test
    void deleteBookById() {
        assertThatCode(() -> bookService.deleteBookById(6L))
                .doesNotThrowAnyException();
    }

    @Test
    void getBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(expectedBook));
        var actualBooks = bookService.getBooks();
        assertThat(actualBooks)
                .containsExactlyInAnyOrder(expectedBook);
    }
}