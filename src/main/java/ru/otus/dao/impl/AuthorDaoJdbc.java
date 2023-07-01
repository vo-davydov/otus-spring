package ru.otus.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Author author) {
        jdbc.update("insert into author (name) values (:name)",
                Map.of("name", author.getName()));
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("select id, name from author where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getByName(String name) {
        return jdbc.query("select id, name from author where UPPER(name) LIKE UPPER(:name)",
                Map.of("name", name), new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            var author = new Author();
            author.setId(id);
            author.setName(name);
            return author;
        }
    }

}
