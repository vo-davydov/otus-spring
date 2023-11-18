package ru.otus.serivce;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    Book getBookById(String id);

    void deleteBookById(String id);

    List<Book> getBooks();

    List<Book> getBooksByAuthor(String name);

    List<Book> getBooksByGenre(String name);

}
