package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/books/")
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/api/books/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/api/books/")
    public void saveBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
    }

    @PutMapping("/api/books/")
    public void editBook(@RequestBody BookDto bookDto) {
        bookService.saveBook(bookDto);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
    }
}
