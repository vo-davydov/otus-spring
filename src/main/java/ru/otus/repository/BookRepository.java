package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> getBooksByAuthor_Name(String authorName);

    List<Book> getBooksByGenre_Name(String genreName);
}
