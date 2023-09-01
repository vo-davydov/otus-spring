package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void saveBook(BookDto bookDto);

    Optional<Book> getBookById(Long id);

    void deleteBookById(Long id);

    List<Book> getBooks();

    Long countBooks();

    List<Book> getBooksByAuthor(String name);

}
