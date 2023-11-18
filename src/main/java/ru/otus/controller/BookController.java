package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books/")
    public Flux<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/api/books/{id}")
    public Mono<Book> getBookById(@PathVariable("id") String id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/api/books/")
    public Mono<Book> saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/api/books/")
    public Mono<Book> editBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookService.deleteBookById(id);
    }

}
