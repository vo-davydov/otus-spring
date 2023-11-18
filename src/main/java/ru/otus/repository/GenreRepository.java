package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {

    @Query("{ 'name' : ?0 }")
    List<Genre> findGenreByName(String name);

}
