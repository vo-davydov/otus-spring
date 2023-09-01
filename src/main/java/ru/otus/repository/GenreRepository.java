package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    Genre findByName(String name);

}
