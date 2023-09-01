package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Long count();

    void save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void delete(Long id);

    List<Book> findBooksByAuthor(String name);

    List<Book> findBooksByName(String name);

}
