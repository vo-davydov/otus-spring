package ru.otus.dao;

import ru.otus.domain.Genre;

public interface GenreDao {

    void insert(Genre genre);

    Genre getByName(String name);
}
