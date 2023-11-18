package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Book;

public interface BookService {

    Mono<Book> saveBook(Book book);

    Mono<Book> getBookById(String id);

    void deleteBookById(String id);

    Flux<Book> getBooks();

    Flux<Book> getBooksByAuthor(String name);

    Flux<Book> getBooksByGenre(String name);

}
