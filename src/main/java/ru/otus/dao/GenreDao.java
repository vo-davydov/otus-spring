package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    Integer count();

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getAll();

    Genre getByName(String name);
}
