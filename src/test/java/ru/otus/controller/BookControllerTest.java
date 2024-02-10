package ru.otus.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.BookService;

@WebFluxTest(controllers = BookController.class)
public class BookControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookService bookService;

    private Book book1;

    private Book book2;

    @BeforeEach
    public void setUp() {
        var genre = new Genre("horror");
        var author = new Author("Ivan");

        book1 = new Book("IT", author, genre);

        var genre2 = new Genre("horror");
        var author2 = new Author("Vac9");

        book2 = new Book("IT2", author2, genre2);
    }

    @Test
    public void testGetAllBooks() {
        Mockito.when(bookService.getBooks()).thenReturn(Flux.just(book1, book2));

        webTestClient.get()
                .uri("/api/books/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .hasSize(2)
                .contains(book1, book2);
    }

    @Test
    public void testGetBookById() {
        String bookId = "1";

        Mockito.when(bookService.getBookById(bookId)).thenReturn(Mono.just(book1));

        webTestClient.get()
                .uri("/api/books/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).isEqualTo(book1);
    }

    @Test
    public void testSaveBook() {
        Mockito.when(bookService.saveBook(book1)).thenReturn(Mono.just(book1));

        webTestClient.post()
                .uri("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(book1), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).isEqualTo(book1);
    }

    @Test
    public void testEditBook() {
        Mockito.when(bookService.saveBook(book1)).thenReturn(Mono.just(book1));

        webTestClient.put()
                .uri("/api/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(book1), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).isEqualTo(book1);
    }

    @Test
    public void testDeleteBook() {
        Mockito.when(bookService.deleteBookById("123")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/books/{id}", "123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }
}
