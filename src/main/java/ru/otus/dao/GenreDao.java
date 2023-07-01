package ru.otus.dao;

import ru.otus.domain.Genre;

public interface GenreDao {

    void insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String name);
}
