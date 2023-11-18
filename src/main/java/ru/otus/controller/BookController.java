package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.domain.Tutorial;
import ru.otus.repository.TutorialRepository;
import ru.otus.service.BookService;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private TutorialRepository tutorialRepository;

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
    public void editBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        return tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    }

    @GetMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Tutorial> getTutorialById(@PathVariable("id") String id) {
        return tutorialRepository.findById(id);
    }
}
