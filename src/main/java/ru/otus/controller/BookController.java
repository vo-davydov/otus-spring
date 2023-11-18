package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.serivce.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book save(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/{name}/author")
    public List<Book> getBooksByAuthor(@PathVariable String name) {
        return bookService.getBooksByAuthor(name);
    }

    @DeleteMapping
    public void deleteBookById(@RequestParam String id) {
        bookService.deleteBookById(id);
    }

   @GetMapping
    public List<Book> getBooks() {
        return bookService.getBooks();
    }
}
