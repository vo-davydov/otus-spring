package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> getBooksByAuthor_Name(String authorName);

    Flux<Book> getBooksByGenre_Name(String genreName);
}
