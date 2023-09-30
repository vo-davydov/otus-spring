package ru.otus.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre (name) values (:name)",
                Map.of("name", genre.getName()));
    }

    public Genre getByName(String name) {
        return jdbc.queryForObject("select id, name from genre where UPPER(name) LIKE UPPER(:name)",
                Map.of("name", name), new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            var genre = new Genre();
            genre.setId(id);
            genre.setName(name);
            return genre;
        }

    }

}
