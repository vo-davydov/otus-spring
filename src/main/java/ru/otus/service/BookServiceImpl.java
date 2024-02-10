package ru.otus.service;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @Override
    public Mono<Book> saveBook(Book book) {
        Mono<Author> savedAuthor = authorRepository.save(book.getAuthor());
        Mono<Genre> savedGenre = genreRepository.save(book.getGenre());

        return savedAuthor
                .zipWith(savedGenre)
                .flatMap(tuple -> {
                    book.setAuthor(tuple.getT1());
                    book.setGenre(tuple.getT2());
                    return bookRepository.save(book);
                });
    }

    @Override
    @Nullable
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteBookById(String id) {
        return bookRepository.deleteById(id);
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
