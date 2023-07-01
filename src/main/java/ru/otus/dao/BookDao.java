package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BookDao {

    Integer count();

    void insert(Book book);

    void update(Book book);

    Book getById(long id);

    List<Book> getAll();

    void delete(Long id);

    List<Book> getBooksByAuthor(String name);
}
