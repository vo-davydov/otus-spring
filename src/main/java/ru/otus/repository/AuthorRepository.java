package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

    @Query("{ 'name' : ?0 }")
    List<Author> findAuthorByName(String name);

}
