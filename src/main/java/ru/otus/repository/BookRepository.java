package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository {

    void save(Book book);

    Book findById(long id);

    List<Book> findAll();

    void delete(Long id);

    List<Book> findBooksByAuthor(String name);

    List<Book> findBooksByName(String name);

}
