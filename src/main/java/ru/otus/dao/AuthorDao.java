package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    Integer count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    Author getByName(String name);

}
