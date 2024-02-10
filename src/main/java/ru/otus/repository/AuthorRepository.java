package ru.otus.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    @Query("{ 'name' : ?0 }")
    Flux<Author> findAuthorByName(String name);

}
