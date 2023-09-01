package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import java.util.List;

public interface BookService {

    void saveBook(BookDto bookDto);

    Book getBookById(Long id);

    void deleteBookById(Long id);

    List<Book> getBooks();

    List<Book> getBooksByAuthor(String name);

}
