package ru.otus.service;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Mono<Book> saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Nullable
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<Book> getBooksByAuthor(String name) {
        return bookRepository.getBooksByAuthor_Name(name);
    }

    @Override
    public Flux<Book> getBooksByGenre(String name) {
        return bookRepository.getBooksByGenre_Name(name);
    }
}
