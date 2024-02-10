package ru.otus.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    @Query("{ 'name' : ?0 }")
    Flux<Genre> findGenreByName(String name);

}
